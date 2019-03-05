package com.bw.fit.component.flow.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import com.bw.fit.component.flow.entity.TCoFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowRegister;
import com.bw.fit.component.flow.mapper.FlowPlusMapper;
import com.bw.fit.component.flow.model.FlowHandle;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.util.PubFun;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.service.FlowCoreService;
import com.bw.fit.component.flow.service.FlowPlusService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class FlowPlusServiceImpl implements FlowPlusService {

	@Resource
	private TaskService taskService ;
	@Resource
	private RuntimeService runtimeService ;
	@Autowired
	private FlowCoreService flowCoreService;
	@Resource
	private FlowPlusMapper flowPlusMapper;

	@Override
	public List<TCoFlowExecuteDefinition> getFlowCoExecutions(String processId) {
		return flowPlusMapper.getFlowCoExecutions(processId);
	}

	@Override
	public List<TFlowExecuteDefinition> getCanBackFlowNodes(String processId) {
		List<TCoFlowExecuteDefinition> list = getFlowCoExecutions(processId);
		if(ObjectUtil.isNotNull(list) && list.size()>0){
			TCoFlowExecuteDefinition tCoFlowExecuteDefinition = list.get(0);

			TFlowExecuteDefinition tFlowExecuteDefinition = new TFlowExecuteDefinition();
			tFlowExecuteDefinition.setTaskDefKey(tCoFlowExecuteDefinition.getNextTaskKey());
			tFlowExecuteDefinition.setProcessDefKey(tCoFlowExecuteDefinition.getProcessDefKey());
			TFlowExecuteDefinition tf2 = flowPlusMapper.getThisNode(tFlowExecuteDefinition);
			tFlowExecuteDefinition.setExecuteNum(tf2.getExecuteNum());

			List<TFlowExecuteDefinition> tfs = flowPlusMapper.getBeforeNodeByCurtNode(tFlowExecuteDefinition);
			for(TFlowExecuteDefinition t:tfs){
				String taskDefName = taskService.createTaskQuery().taskDefinitionKey(t.getTaskDefKey()).singleResult().getName();
				t.setRemark(taskDefName);
			}
			return tfs;
		}
		return  null;
	}

	@Transactional(rollbackFor ={Exception.class,RbackException.class})
	@Override
	public JSONObject createRegisterPInstance(TFlowRegister tFlowRegister) throws RbackException {
		JSONObject jsonObject = new JSONObject();
		try{
			flowPlusMapper.createRegisterPInstance(tFlowRegister);
			PubFun.returnSuccessJson(jsonObject);
		}catch (Exception e){
			e.printStackTrace();
			PubFun.returnFailJson(jsonObject,"流程登记异常");
			throw  new RbackException("1","流程登记异常");
		}finally {
			return jsonObject;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject createHandleFlow(FlowHandle flowHandle) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Task currentTask =taskService.createTaskQuery().taskId(flowHandle.getTaskId()).singleResult();
		if(ObjectUtil.isNull(currentTask)){
			PubFun.returnFailJson(jsonObject,"任务不存在");
			return jsonObject;
		}
		if("pass".equalsIgnoreCase(flowHandle.getHandleOpt())){
			flowCoreService.cliamTaskToUser(flowHandle.getTaskId(),flowHandle.getCreator());
			flowCoreService.createTaskComment(currentTask,flowHandle.getRemark());
			flowCoreService.completeTask(flowHandle.getTaskId());
			PubFun.returnSuccessJson(jsonObject);
		}else if("reject".equalsIgnoreCase(flowHandle.getHandleOpt())){
			flowCoreService.cliamTaskToUser(flowHandle.getTaskId(),flowHandle.getCreator());
			flowCoreService.createTaskComment(currentTask,flowHandle.getRemark());
			flowCoreService.rollBack(flowHandle.getpInstanceId(),flowHandle.getToTaskDefKey(),"-1");
			PubFun.returnSuccessJson(jsonObject);
		}else if("proxy".equalsIgnoreCase(flowHandle.getHandleOpt())){
			flowCoreService.cliamTaskToUser(flowHandle.getTaskId(),flowHandle.getCreator());
			flowCoreService.createTaskComment(currentTask,flowHandle.getRemark());
			flowCoreService.createTaskAssignee(flowHandle.getTaskId(),flowHandle.getToHandler());
			PubFun.returnSuccessJson(jsonObject);
		}else{
			PubFun.returnFailJson(jsonObject,"暂不支持"+flowHandle.getHandleOpt()+"方式");
		}
		return  jsonObject;
	}
}
