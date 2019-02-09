package com.bw.fit.component.flow.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.util.ProcessDiagramGenerator;
import com.bw.fit.component.form.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.task.Task;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.service.FlowPlusService;


/*****
 * 流程模块
 * @author yangh
 *
 */
@RequestMapping("flow")
@Controller
@EnableEurekaClient
public class FlowController {

	@Resource
	private ProcessEngine processEngine;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private TaskService taskService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private HistoryService historyService;
	@Autowired
	private FlowPlusService flowPlusService;

	/**
	 * 打开流程图显示页面
	 **/
	@RequestMapping(value = "openActivitiProccessImagePage/{pProcessInstanceId}")
	public ModelAndView openActivitiProccessImagePage(
			@PathVariable String pProcessInstanceId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("processInstanceId", pProcessInstanceId);
		modelAndView.setViewName("component/flow/flowImagePage");
		return modelAndView;
	}

	/**
	 * 打开流程图显示页面
	 **/
	@RequestMapping(value = "openActivitiProccessImage/{taskId}")
	public ModelAndView openActivitiProccessImage(
			@PathVariable String taskId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult(); 
		if(task == null){
			modelAndView.setViewName("component/flow/flowNotExsitePage");
			return modelAndView;
		}
		String pProcessInstanceId = task.getProcessInstanceId();
		modelAndView.addObject("processInstanceId", pProcessInstanceId);
		modelAndView.setViewName("component/flow/flowImagePage");
		return modelAndView;
	}

	/**
	 * 获取流程图像，已执行节点和流程线高亮显示
	 */
	@RequestMapping(value = "getActivitiProccessImage/{pProcessInstanceId}")
	public void getActivitiProccessImage(@PathVariable String pProcessInstanceId,
			HttpServletResponse response) throws Exception {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			// 获取历史流程实例
			HistoricProcessInstance historicProcessInstance = historyService
					.createHistoricProcessInstanceQuery()
					.processInstanceId(pProcessInstanceId).singleResult();

			if (historicProcessInstance == null) {
				throw new RbackException("1", "获取流程实例ID[" + pProcessInstanceId
						+ "]对应的历史流程实例失败！");
			} else {
				// 获取流程定义
				ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
						.getDeployedProcessDefinition(historicProcessInstance
								.getProcessDefinitionId());

				// 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
				List<HistoricActivityInstance> historicActivityInstanceList = historyService
						.createHistoricActivityInstanceQuery()
						.processInstanceId(pProcessInstanceId)
						.orderByHistoricActivityInstanceId().asc().list();

				// 已执行的节点ID集合
				List<String> executedActivityIdList = new ArrayList<String>();
				int index = 1;
				for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
					executedActivityIdList
							.add(activityInstance.getActivityId());
					index++;
				}
				// 获取流程图图像字符流
				InputStream imageStream = ProcessDiagramGenerator
						.generateDiagram(processDefinition, "png",
								executedActivityIdList);

				response.setContentType("image/png");
				OutputStream os = response.getOutputStream();
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				imageStream.close();
			}
		} catch (Exception e) {
			throw new RbackException("1", "获取流程图失败！" + e.getMessage());
		}
	}

	/*****
	 * 工作流系统对外提供的流程流转所有操作,前提是该任务存在
	 * @return
	 */
	@GetMapping("handleOpt/{taskId}")
	@ResponseBody
	public  String getAllOpts(@PathVariable String taskId){
		List<Task> tasks = taskService.createTaskQuery().taskId(taskId).list();
		if(tasks == null || tasks.size()<1){
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		List<BaseModel> list= new ArrayList<>();
		BaseModel baseModel= new BaseModel();
		baseModel.setCode("pass");
		baseModel.setRemark("通过");
		list.add(baseModel);
		baseModel= new BaseModel();
		baseModel.setCode("reject");
		baseModel.setRemark("驳回");
		list.add(baseModel);
		baseModel= new BaseModel();
		baseModel.setCode("proxy");
		baseModel.setRemark("转办");
		list.add(baseModel);
		jsonObject.put("total", list.size());
		jsonObject.put("rows", JSONObject.toJSON(list));
		return jsonObject.toJSONString();
	}

	/*****
	 * 根据流程实例id，查看当前能驳回到哪些节点
	 * @param pdInstId
	 * @return
	 */
	@GetMapping("backNode/{pdInstId}")
	@ResponseBody
	public String canBackNode(@PathVariable String pdInstId){
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pdInstId).list();
		if(tasks == null || tasks.size()<1){//流程实例已经结束
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		List<TFlowExecuteDefinition> tfs = flowPlusService.getCanBackFlowNodes(pdInstId);
		if(CollectionUtil.isNotEmpty(tfs)){
			jsonObject = (JSONObject)JSONObject.toJSON(tfs);
			jsonObject.put("res","2");

		}else{
			jsonObject = new JSONObject();
			jsonObject.put("res","1");
			jsonObject.put("msg","无数据");
		}
		return jsonObject.toJSONString();
	}

}
