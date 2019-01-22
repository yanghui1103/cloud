package com.bw.fit.component.flow.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.dao.FlowDao;
import com.bw.fit.component.flow.entity.TAudit;
import com.bw.fit.component.flow.entity.TFlowBranch;
import com.bw.fit.component.flow.entity.TNode2Dealer;
import com.bw.fit.component.flow.entity.TTodo;
import com.bw.fit.component.flow.model.Audit;
import com.bw.fit.component.flow.service.FlowCoreService;
import com.bw.fit.component.flow.service.FlowPlusService;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.OaTodoUtils;
import com.bw.fit.system.common.util.PubFun;
import com.landray.kmss.sys.notify.webservice.NotifyTodoAppResult;
import com.landray.kmss.sys.notify.webservice.NotifyTodoRemoveContext;
@Service
public class FlowPlusServiceImpl implements FlowPlusService {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private TaskService taskService ;
	@Autowired
	private RuntimeService runtimeService ;
	@Autowired
	private FlowCoreService flowCoreService;
	@Autowired
	private FlowDao flowDao;
	
	@Override
	public ProcessInstance createNewFlowAndStart(String flowKey,
			Map<String, Object> vars) {
		ProcessInstance pi = flowCoreService.startProcessInstanceByKey(flowKey , vars);
		return pi;
	}

	@Override
	public List<TNode2Dealer> getNodeDealersConf(String nodeCode,String pdinst) {
		return flowDao.getNodeDealersConf(nodeCode,pdinst);
	}

	@Override
	public void makNodeAllDealersOfPdInst(String pdinst,
			Map<String, Object> dealers) {
		List<TNode2Dealer> lis = flowDao.getNodeDealersConf(null,pdinst); 
		if(lis !=null &&lis.size()>0){
			List<String> nodes = lis.stream().map(x->x.getNodeCode()).collect(Collectors.toList());
			for(String n:nodes){
				dealers.put(n, lis.stream().filter(x->x.getNodeCode().equals(n)).map(x->x.getDealer()).collect(Collectors.joining(",")));
			}
		}
	}

	@Override
	public JSONObject createOneForeignMultiFlow(TFlowBranch tb)
			throws RbackException {
		JSONObject json = new JSONObject();
		try {
			flowDao.createOneForeignMultiFlow(tb);
			json.put("res", "2");
			json.put("msg", "创建流程分支成功");
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			json.put("res", "1");
			json.put("msg", e.getMsg());
			throw e;
		}finally{
			return json ;
		}
	}

	@Override
	public JSONObject createTodo(TTodo td) throws RbackException {
		JSONObject json = new JSONObject();
		try {
			flowDao.createTodo(td);
			json.put("res", "2");
			json.put("msg", "创建成功");
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			json.put("res", "1");
			json.put("msg", e.getMsg());
			throw e;
		}finally{
			return json ;
		}
	}

	@Override
	public JSONObject createDraftTodo(TTodo td) throws RbackException {
		JSONObject json = new JSONObject();
		try {
			td.setId(PubFun.getUUID());
			td.setStatus("66");
			flowDao.createTodo(td);
			json.put("res", "2");
			json.put("msg", "起草成功");
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			json.put("res", "1");
			json.put("msg", e.getMsg());
			throw e;
		}finally{
			return json ;
		}
	}

	@Override
	public JSONObject completeTodo(TTodo td) throws RbackException {
		td.setStatus("65"); /***已处理**/
		JSONObject json = new JSONObject();
		try {
			flowDao.updateTodo(td);
			json.put("res", "2");
			json.put("msg", "创建成功");
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			json.put("res", "1");
			json.put("msg", e.getMsg());
			throw e;
		}finally{
			return json ;
		}
	}

	@Override
	public JSONObject audit(Audit audit) throws RbackException {
		JSONObject json = new JSONObject();
		PubFun.returnSuccessJson(json);
		try {
			if("rejectp".equalsIgnoreCase(audit.getAuditOpt())){
				JSONObject j = sendTodoForYt("专家库",audit.getFormKey(),"ZJK",audit.getTaskId());
				flowCoreService.cliamTaskToUser(audit.getTaskId(), PubFun.getCurrentAccount().getId());
				flowCoreService.rollBackProcess(audit.getTaskId());
				
				TAudit ta = new TAudit();
				PubFun.copyProperties(ta, audit);
				flowDao.createFlowDeal(ta);
				
				return j ;
			}else if("tongyi".equalsIgnoreCase(audit.getAuditOpt())){
				JSONObject j = sendTodoForYt("专家库",audit.getFormKey(),"ZJK",audit.getTaskId());
				flowCoreService.cliamTaskToUser(audit.getTaskId(), PubFun.getCurrentAccount().getId());
				flowCoreService.completeTask(audit.getTaskId());

				TAudit ta = new TAudit();
				PubFun.copyProperties(ta, audit);
				flowDao.createFlowDeal(ta);
				
				return j ;
			}else if("rejectToDft".equalsIgnoreCase(audit.getAuditOpt())){
				JSONObject j = sendTodoForYt("专家库",audit.getFormKey(),"ZJK",audit.getTaskId());
				Task task=taskService.createTaskQuery() // 创建任务查询
		                .taskId(audit.getTaskId()) // 根据任务id查询
		                .singleResult(); 
				flowCoreService.cliamTaskToUser(audit.getTaskId(), PubFun.getCurrentAccount().getId());
				taskService.setVariable(audit.getTaskId(), "flowDirect", "reverse");
				flowCoreService.rollBack(task.getProcessInstanceId(),"draftsman","");	
				TAudit ta = new TAudit();
				PubFun.copyProperties(ta, audit);
				flowDao.createFlowDeal(ta);
				
				return j ;
			}else{
				PubFun.returnFailJson(json, "未知操作，操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = new JSONObject(); 
			PubFun.returnFailJson(json, "流程处理异常");
			throw new RbackException("1","流程处理异常");
		}
		return json;
	}

	@Override
	public JSONObject sendTodoForYt(String appname,String modelId,String modelname,String taskId) throws RbackException {
		JSONObject json = new JSONObject();
		NotifyTodoRemoveContext context = new NotifyTodoRemoveContext();
		context.setAppName(appname);
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		context.setModelId(modelId + task.getTaskDefinitionKey());
		context.setOptType(1);
		context.setKey(modelId);
		String loginname =accountService.get(PubFun.getCurrentAccount().getId()).getLogName() ;
		context.setTargets("{\"LoginName\":\""+loginname+"\"}");
		NotifyTodoAppResult result = OaTodoUtils.setTodoDone(context);
		json.put("res", result.getReturnState());
		json.put("msg", "".equals(result.getMessage())?"执行成功":result.getMessage());
		System.out.println(" 置为已办res "+result.getReturnState());
		return json ;
	}


	@Override
	public JSONObject deletePiPlus(Audit audit) throws RbackException {
		JSONObject json = new JSONObject();
		TAudit ta = new TAudit();
		PubFun.copyProperties(ta, audit);
		flowDao.createFlowDeal(ta);
		Task task =(Task)taskService.createTaskQuery().taskId(audit.getTaskId()).singleResult();
		if(task == null){
			PubFun.returnFailJson(json, "流程任务不存在");
			return json ;
		}
		flowCoreService.deleteProcessInstance(task.getProcessInstanceId(), audit.getRemark());		
		return json;
	}

	@Override
	public JSONObject getExisteFlows(String zjId) {
		JSONObject json = new JSONObject();
		List<String> taskIds = flowDao.getTasksFromNotifyTodo(zjId);
		if(taskIds !=null){
			for(String taskId:taskIds){
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				if(task !=null){
					String processInstanceId=task.getProcessInstanceId(); // 获取流程实例id
			        ProcessInstance pi=runtimeService.createProcessInstanceQuery() // 根据流程实例id获取流程实例
			                .processInstanceId(processInstanceId)
			                .singleResult();
			        if(pi !=null){
			        	PubFun.returnFailJson(json, "用户存在尚未审核完毕的申请流程");
			        	return json ;
			        }
				}
			}
		}
		json.put("res", "2");
		json.put("msg", "用户不存在未审核完毕的流程");
		return json ;
	}

	@Override
	public JSONObject createNodeDealer(TNode2Dealer nd) throws RbackException {
		JSONObject json = new JSONObject();
		try {
			if(flowDao.getNodeDealersConf(nd.getNodeCode(),nd.getPdinst()) != null ){
				flowDao.deleteNodeDealersConf(nd.getNodeCode(),nd.getPdinst());
			}
			String[] array = nd.getDealer().split(",");
			for(String dealer :array){
				nd.setId(PubFun.getUUID());
				nd.setTypeCode("vie");
				nd.setDealer(dealer);
				flowDao.createNodeDealer(nd);
			}
			json = new JSONObject();
			PubFun.returnSuccessJson(json);
		} catch (RbackException e) {
			e.printStackTrace();
			json = new JSONObject();
			json.put("res", "1");
			json.put("msg", e.getMsg());
			throw e;
		}finally{
			return json ;
		}
	}
	
	
}
