package com.bw.fit.component.flow.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.bw.fit.component.flow.conf.FlowHandleWays;
import com.bw.fit.component.flow.entity.BaseEntity;
import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowRegister;
import com.bw.fit.component.flow.mapper.FlowPlusMapper;
import com.bw.fit.component.flow.model.FlowHandle;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.model.Todo;
import com.bw.fit.component.flow.service.CommonService;
import com.bw.fit.component.flow.service.FlowCoreService;
import com.bw.fit.component.flow.util.ProcessDiagramGenerator;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.model.BaseModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import io.swagger.annotations.Api;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.service.FlowPlusService;

import static com.bw.fit.component.flow.util.PubFun.returnFailJson;


/*****
 * 流程模块
 * @author yangh
 *
 */
@Api("flow提供接口")
@RequestMapping(value="flow",produces = "application/json; charset=utf-8")
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
	@Resource
	private FlowPlusMapper flowPlusMapper;
	@Autowired
	private FlowCoreService flowCoreService;
	@Autowired
	private CommonService commonService;
	@Resource
	FlowHandleWays flowHandleWays;

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
		List<Map<String,String>> listMap = flowHandleWays.getListMap();
		if(CollectionUtil.isNotEmpty(listMap)){
			listMap.parallelStream().forEach(x->{
				BaseModel baseModel= new BaseModel();
				baseModel.setCode(x.get("code"));
				baseModel.setRemark(x.get("name"));
				list.add(baseModel);
			});
		}

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

	/*****
	 * 所有流程执行定义列表,处理人及执行顺序等
	 * @return
	 */
	@GetMapping("processDefinition")
	@ResponseBody
	public String processDefinition(){
		JSONArray jsonArray = new JSONArray();
		List<TFlowExecuteDefinition> tFlowExecuteDefinitionList = flowPlusMapper.getAllFlowDefs(null);
		if(CollectionUtil.isNotEmpty(tFlowExecuteDefinitionList)){
			jsonArray = (JSONArray)JSONArray.toJSON(tFlowExecuteDefinitionList);
			return jsonArray.toJSONString();
		}
		return  null;
	}

	/****
	 * 该账户的待办
	 * @param httpServletRequest
	 * @return
	 */
	@GetMapping("todoTasks")
	@ResponseBody
	public String todoTasks(HttpServletRequest httpServletRequest, @ModelAttribute BaseModel baseModel){
		JSONObject accountJson = commonService.getCurrentAccount(httpServletRequest);
		JSONObject jsonObject = new JSONObject();
		List<Task> tasks = new ArrayList<>();
		List<Todo> todos = new ArrayList<>();
		List<Task> tasks1 = flowCoreService.getTasksOfTheUser(accountJson.getString("id"));
		List<Task> tasks2 = flowCoreService.getTasksOfTheAssginee(accountJson.getString("id"));
		tasks.addAll(tasks1);
		tasks.addAll(tasks2);

		if(CollectionUtil.isNotEmpty(tasks)){
			for(Task task:tasks){
				Todo todo = new Todo();
				TFlowRegister tFlowRegister = flowPlusMapper.getFlowRegsByFlowId(task.getProcessInstanceId()).get(0);
				PubFun.copyProperties(todo,tFlowRegister);
				todo.setTaskId(task.getId());
				todo.setCreateTime(PubFun.formatDate(task.getCreateTime()));
				todos.add(todo);
			}
			jsonObject.put("rows",JSONObject.toJSONString(todos.subList(baseModel.getPage()*baseModel.getRows(),baseModel.getPage()*baseModel.getRows()+baseModel.getRows())));
		}
		jsonObject.put("total",todos.size());
		return jsonObject.toJSONString();
	}

	/****
	 * 该账户的已办列表
	 * @return
	 */
	@GetMapping("doneTasks")
	@ResponseBody
	public JSONObject doneTasks(HttpServletRequest httpServletRequest, @ModelAttribute BaseModel baseModel){
		JSONObject jsonObject = new JSONObject();
		JSONObject accountJson = commonService.getCurrentAccount(httpServletRequest);
		List<Todo> historyTasks = new ArrayList<>();
		List<HistoricTaskInstance> historicTaskInstancess = flowCoreService.getUserhistoryTaskInstance(accountJson.getString("id"),false);
		if(CollectionUtil.isNotEmpty(historicTaskInstancess)){
			for(HistoricTaskInstance task:historicTaskInstancess){
				Todo todo = new Todo();
				TFlowRegister tFlowRegister = flowPlusMapper.getFlowRegsByFlowId(task.getProcessInstanceId()).get(0);
				PubFun.copyProperties(todo,tFlowRegister);
				todo.setTaskId(task.getId());
				todo.setCreateTime(PubFun.formatDate(task.getCreateTime()));
				historyTasks.add(todo);
			}
			jsonObject.put("rows",JSONObject.toJSONString(historyTasks.subList(baseModel.getPage()*baseModel.getRows(),baseModel.getPage()*baseModel.getRows()+baseModel.getRows())));
		}
		jsonObject.put("total",historyTasks.size());
		return jsonObject;
	}

	/*****
	 * 启动流程
	 * @param processDefinitionKey 定义key
	 * @param title 主题
	 * @param vars 键值对，格式是jsonstring
	 * @return 返回流程实例id：pInstanceId
	 */
	@PostMapping("start/processDefinitionKey/{processDefinitionKey}/{title}/{vars}")
	@ResponseBody
	public JSONObject startFlow(HttpServletRequest httpServletRequest, @PathVariable String title, @PathVariable String processDefinitionKey,@PathVariable String vars) throws RbackException {
		JSONObject jsonObject = new JSONObject();
		JSONObject accountJson = commonService.getCurrentAccount(httpServletRequest);
		Map map = JSONObject.toJavaObject((JSONObject)JSONObject.toJSON(vars),Map.class);
		map.put("drafter",accountJson.getString("id"));
		ProcessInstance pInstance=processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey,map);

		if(ObjectUtil.isNotNull(pInstance)){
			jsonObject.put("pInstanceId",pInstance.getId());
			// 流程登记
			TFlowRegister tFlowRegister = new TFlowRegister();
			tFlowRegister.setTitle(title);
			tFlowRegister.setFlowId(pInstance.getId());
			tFlowRegister.setDrafter(accountJson.getString("id"));
			commonService.fillCommonProptities(tFlowRegister,httpServletRequest,true);
			jsonObject = flowPlusService.createRegisterPInstance(tFlowRegister);
		}else{
			jsonObject = new JSONObject();
			returnFailJson(jsonObject,"启动失败");
		}
		return jsonObject;
	}

	/*****
	 * 某个发起者的所有发起的申请，翻页
	 * @param drafter
	 * @return
	 */
	@GetMapping("flow/drafter/{drafter}")
	@ResponseBody
	public JSONObject getFlowsOfDrafter(@PathVariable String drafter,@ModelAttribute BaseEntity baseEntity, @RequestParam(name = "tt") String tt, HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		PageHelper.startPage(baseEntity.getPage(),baseEntity.getRows());
		Page<TFlowRegister> tFlowRegisters = flowPlusMapper.getPInstanceOfDrafter(drafter);
		jsonObject.put("total",tFlowRegisters.getTotal());
		if(CollectionUtil.isNotEmpty(tFlowRegisters)){
			jsonObject.put("rows",(JSONObject)JSONObject.toJSON(tFlowRegisters));
		}
		return jsonObject ;
	}

	/******
	 * 流程办理
	 */
	@PostMapping("handle")
	@ResponseBody
	public JSONObject handle(@Valid @ModelAttribute FlowHandle flowHandle, BindingResult bindingResult, HttpServletRequest httpServletRequest){
		JSONObject jsonObject = new JSONObject();
		if (bindingResult.hasErrors()) {
			FieldError error = bindingResult.getFieldError();
			jsonObject.put("res", "1");
			returnFailJson(jsonObject, error.getDefaultMessage());
			return jsonObject ;
		}
		return  null;
	}

}
