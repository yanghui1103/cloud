package com.bw.fit.component.flow.listener.impl;

import java.util.List;
import java.util.Set;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.dao.FlowDao;
import com.bw.fit.component.flow.entity.TTodo;
import com.bw.fit.component.flow.listener.TodoreadService;
import com.bw.fit.component.flow.service.FlowPlusService;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.util.OaTodoUtils;
import com.bw.fit.system.common.util.PropertiesUtil;
import com.bw.fit.system.common.util.PubFun;
import com.landray.kmss.sys.notify.webservice.NotifyTodoAppResult;
import com.landray.kmss.sys.notify.webservice.NotifyTodoRemoveContext;
import com.landray.kmss.sys.notify.webservice.NotifyTodoSendContext;
/*****
 * 用于普通节点上，任务创建，任务完成，分配任务处理人，
 * 所触发的监听器。
 * @author yangh
 *
 */
@Service(value="standardTaskLisenter")
public class StandardTaskLisenterImpl implements TaskListener{
	@Autowired
	private FlowPlusService flowPlusService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FlowDao flowDao;
	
	@Override
	public void notify(DelegateTask task) {
		try {
			System.out.println(task.getTaskDefinitionKey());
			createNotify(task);
		} catch (RbackException e) {
			e.printStackTrace();
		}
	}
	
	public void createNotify(DelegateTask task) throws RbackException {
		String formKey= (String) runtimeService.getVariable(task.getExecutionId(), "formKey");
		String foreignId = (String) runtimeService.getVariable(task.getExecutionId(), "foreignId");
		if(task!=null && "create".equalsIgnoreCase(task.getEventName())){
			try {
				TTodo td = new TTodo();
				td.setId(PubFun.getUUID());
				td.setTaskId(task.getId());
				td.setParentId(formKey);
				td.setCreator(PubFun.getCurrentAccount().getId());
				td.setCreateOrgId(PubFun.getCurrentAccount().getCurrentOrgId());
				td.setTitle(task.getVariable("title").toString());
				td.setPath("flow/auditDetail/"+formKey+"/"+task.getId()+"/"+(foreignId==null?td.getId():foreignId)); 
				td.setAccountId(task.getTaskDefinitionKey());
				td.setTypeCode("todo");
				td.setStatus("64");
				
				JSONObject j = flowPlusService.createTodo(td);
				if("1".equals(j.get("res").toString())){ 
					throw new RbackException("1","创建待办失败："+j.getString("msg").toString());
				}

				NotifyTodoSendContext context = new NotifyTodoSendContext();
				context.setAppName("专家库");
				context.setSubject(td.getTitle());
				context.setLink(PropertiesUtil.getValueByKey("system.default.url") +td.getPath());
				context.setType(1);
				context.setKey(task.getVariable("formKey").toString());
				context.setModelId(task.getVariable("formKey").toString()+task.getTaskDefinitionKey());
				Set<IdentityLink> sets = task.getCandidates();
				if(sets !=null && sets.size()>0 ){
					sets.stream().forEach(x->{ // 往出推送待办
						String loginname =accountService.get(x.getUserId()).getLogName() ;
						context.setTargets("{\"LoginName\":\""+loginname+"\"}");
						context.setCreateTime(PubFun.getSysDate());
						List lis = flowDao.getFlowByParentId(task.getVariable("formKey").toString());
 						if(lis != null    && !task.getTaskDefinitionKey().equalsIgnoreCase("draftsman") ){
							NotifyTodoAppResult result = OaTodoUtils.sendTodo(context);
							System.out.println(result.getReturnState());
							System.out.println(result.getMessage());
						}
					});
				}else if(!"".equals(task.getAssignee())){
					String loginname =accountService.get(task.getAssignee()).getLogName() ;
					context.setTargets("{\"LoginName\":\""+loginname+"\"}");
					context.setCreateTime(PubFun.getSysDate());
					List lis = flowDao.getFlowByParentId(task.getVariable("formKey").toString());
					if(lis != null  && !task.getTaskDefinitionKey().equalsIgnoreCase("draftsman") ){
						NotifyTodoAppResult result = OaTodoUtils.sendTodo(context);
						System.out.println(result.getReturnState());
						System.out.println(result.getMessage());
					}
				}
			} catch (RbackException e) {
				e.printStackTrace();
				throw e;
			}
		}else 
			if(task!=null && "complete".equalsIgnoreCase(task.getEventName())){
				String processInstanceId=task.getProcessInstanceId(); // 获取流程实例id
		        ProcessInstance pi=runtimeService.createProcessInstanceQuery() // 根据流程实例id获取流程实例
		                .processInstanceId(processInstanceId)
		                .singleResult();	
		        taskService.setVariable(task.getId(), "parentTaskKey", task.getTaskDefinitionKey());
		        runtimeService.setVariable(task.getExecutionId(), "backActivityId", pi.getActivityId());
//				TTodo td = new TTodo();
//				td.setId(PubFun.getUUID());
//				td.setTaskId(task.getId());
//				td.setParentId(formKey);
//				td.setCreator(PubFun.getCurrentAccount().getId());
//				td.setCreateOrgId(PubFun.getCurrentAccount().getCurrentOrgId());
//				td.setTitle(task.getVariable("title").toString());
//				td.setPath("flow/auditDetail/"+formKey+"/"+task.getId()); 
//				td.setAccountId(task.getTaskDefinitionKey());
//				td.setTypeCode("todo");
//				td.setStatus("65");
//				
//				JSONObject j = flowPlusService.createTodo(td);
//				if("1".equals(j.get("res").toString())){ 
//					throw new RbackException("1","待办办结失败："+j.getString("msg").toString());
//				}
			}
	}

}
