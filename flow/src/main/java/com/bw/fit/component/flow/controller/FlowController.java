package com.bw.fit.component.flow.controller;

import static com.bw.fit.system.common.util.PubFun.returnFailJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.dao.FlowDao;
import com.bw.fit.component.flow.entity.TNode2Dealer;
import com.bw.fit.component.flow.entity.TTodo;
import com.bw.fit.component.flow.model.Audit;
import com.bw.fit.component.flow.service.FlowPlusService;
import com.bw.fit.plan.admin.dao.PlanDao;
import com.bw.fit.plan.admin.entity.TPlan;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.dict.service.DictService;
import com.bw.fit.zj.admin.dao.ZjDao;
import com.bw.fit.zj.admin.entity.TZj;

/*****
 * 流程模块
 * @author yangh
 *
 */
@RequestMapping("flow")
@Controller
public class FlowController {

	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
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
	

	@RequestMapping(value="audit",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject audit(@ModelAttribute Audit au,BindingResult result) throws RbackException{
		JSONObject json = new JSONObject();
		if (result.hasErrors()) {
			FieldError error = result.getFieldError();
			json.put("res", "1");
			returnFailJson(json, error.getDefaultMessage());
			return json;
		}
		Session session = PubFun.getCurrentSession();
		PubFun.fillCommonProptities(au, false, session);
		json = flowPlusService.audit(au);
		return json ;
	}
	
	@RequestMapping(value="todos",method=RequestMethod.GET)
	@ResponseBody
	public JSONArray todos(){
		Account account = PubFun.getCurrentAccount();
		List<TTodo> ds = flowDao.getRelationFlows(account.getId());
		return (JSONArray)JSONArray.toJSON(ds) ;
	}
	
	@RequestMapping(value="auditDetail/{id}/{taskId}/{foreignId}",method=RequestMethod.GET)
	public String auditDetail(@PathVariable String id,@PathVariable String taskId,
			@PathVariable String foreignId,Model model){
		model.addAttribute("id", id);
		model.addAttribute("taskId",taskId);
		model.addAttribute("foreignId",foreignId);
		List<TTodo> dos = flowDao.getDealsOfPid(id);
		model.addAttribute("deals", dos);
		/****
		 * 此处后期最好使用策略模式，此次我就不了
		 */
		TZj t = zjDao.getCo(id);
		TZj tg = zjDao.getCo(foreignId);
		TPlan tp = planDao.get(id);
		if(t!=null && ( t.getCoType().equals("registerzj") || t.getCoType().equals("editzj"))){
			return "forward:/zj/audit/"+id+"/"+taskId ;
		}else if(tg!=null && (tg.getCoType().equals("unshield") || tg.getCoType().equals("shield") || tg.getCoType().equals("exitzj"))){
			return "forward:/zj/audit2/"+id+"/"+taskId+"/"+foreignId ;
		}else if(tp !=null && !"-9".equals(taskId) ){
			return "forward:/plan/audit/"+id+"/"+taskId+"/"+foreignId ;
		}else  if(tp !=null && "-9".equals(taskId) ){
			return "forward:/bounty/audit/"+id+"/"+taskId;
		}
		
		return "component/flow/auditDetail";
	}
	
	@RequestMapping(value="flow/{id}/{taskId}",method=RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(@ModelAttribute Audit audit,@PathVariable(value="id") String id,@PathVariable(value="taskId") String taskId) throws Exception{
		audit.setId(id);
		audit.setTaskId(taskId);
		JSONObject json = flowPlusService.deletePiPlus(audit);
		return json ;
	}
	
	@RequestMapping(value="dealers",method=RequestMethod.GET)
	@ResponseBody
	public JSONArray dealers(){
		JSONArray json = new JSONArray();
		List<TNode2Dealer> nds = flowDao.getNodeDealersConf(null, null);
		for(TNode2Dealer node:nds){
			node.setTypeCode(dictService.getDictByValue(node.getTypeCode())==null?"":dictService.getDictByValue(node.getTypeCode()).getDictName());
			node.setPdinst(dictService.getDictByValue(node.getPdinst())==null?"":dictService.getDictByValue(node.getPdinst()).getDictName());
			node.setNodeCode(dictService.getDictByValue(node.getNodeCode())==null?"":dictService.getDictByValue(node.getNodeCode()).getDictName());
			String dealers = node.getDealer();
			StringBuffer sb = new StringBuffer();
			if(!"".equals(dealers)){
				String[] arr = dealers.split(",");
				for(String s:arr){
					sb.append(accountService.get(s).getName());
					sb.append(",");
				}
				node.setDealer(sb.toString());
			}
			node.setOperator(accountService.get(node.getOperator())==null?"":accountService.get(node.getOperator()).getName());
		}
		if(nds !=null){
			json = (JSONArray)JSONArray.toJSON(nds);
		}
		return json ;
	}
	
	@RequestMapping(value="dealer/{pdinst}/{node}")
	public String dealerCrtPage(@PathVariable(value="pdinst") String pdinst,
			@PathVariable(value="node") String node,
			Model model){
		TNode2Dealer tg = new TNode2Dealer();
		tg.setPdinst(pdinst);
		tg.setNodeCode(node);
		List<TNode2Dealer> list = flowDao.getDealerConf(tg);
		model.addAttribute("pdinstCode",list.get(0).getPdinst());
		model.addAttribute("nodeCode",list.get(0).getNodeCode());
		model.addAttribute("pdinst",(dictService.getDictByValue(list.get(0).getPdinst())==null?"":dictService.getDictByValue(list.get(0).getPdinst()).getDictName()));
		model.addAttribute("node",(dictService.getDictByValue(list.get(0).getNodeCode())==null?"":dictService.getDictByValue(list.get(0).getNodeCode()).getDictName()));
		model.addAttribute("type", (dictService.getDictByValue(list.get(0).getTypeCode())==null?"":dictService.getDictByValue(list.get(0).getTypeCode()).getDictName()));
		StringBuffer sbIds = new StringBuffer();
		StringBuffer sbNames = new StringBuffer();
		list.stream().forEach(x->{
			sbIds.append(x.getDealer());
			sbNames.append(accountService.get(x.getDealer()).getName());
			sbIds.append(",");
			sbNames.append(",");
		});
		model.addAttribute("names", sbNames.toString());
		model.addAttribute("ids", sbIds.toString());
		return "component/flow/dealerCreatePage";
	}
	
	@RequestMapping(value="dealer",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject save(@ModelAttribute TNode2Dealer nodeDealer,@RequestParam(value="accountIds") String accountIds) throws Exception{
		JSONObject json = new JSONObject();
		nodeDealer.setDealer(accountIds);
		Session session  = PubFun.getCurrentSession();
		PubFun.fillCommonProptities(nodeDealer, true, session);
		json = flowPlusService.createNodeDealer(nodeDealer);
		return json ;
	}
}
