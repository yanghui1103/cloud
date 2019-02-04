package com.bw.fit.component.flow.listener.impl;

import com.bw.fit.component.flow.entity.TCoFlowExecuteDefinition;
import com.bw.fit.component.flow.listener.HandledEventListener;
import com.bw.fit.component.flow.mapper.FlowCoreMapper;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.service.FlowCoreService;
import com.bw.fit.component.flow.util.PubFun;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 9:33
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service("handledEventListener")
public class HandledEventListenerImpl implements HandledEventListener, TaskListener {
    @Resource
    private FlowCoreMapper flowCoreMapper;
    @Autowired
    private FlowCoreService flowCoreService;

    @Override
    public void notify(DelegateTask delegateTask) {
        if("delete".equalsIgnoreCase(delegateTask.getEventName())){
            String processDefKey = delegateTask.getProcessDefinitionId();
            String processInstId = delegateTask.getProcessInstanceId();
            String taskDefKey = delegateTask.getTaskDefinitionKey();
            String taskId = delegateTask.getId();
            String handler = delegateTask.getAssignee();
            String handleOpt = delegateTask.getVariable("handleOpt").toString();
            String handleRemark = delegateTask.getVariable("handleRemark").toString();
            String nextNodeId = flowCoreService.getNextNode(processInstId);
            String formKey  = delegateTask.getVariable("formKey").toString();
            TCoFlowExecuteDefinition tCoFlowExecuteDefinition = new TCoFlowExecuteDefinition();
            tCoFlowExecuteDefinition.setId(PubFun.getUUID());
            tCoFlowExecuteDefinition.setProcessDefKey(processDefKey);
            tCoFlowExecuteDefinition.setProcessId(processInstId);
            tCoFlowExecuteDefinition.setTaskDefKey(taskDefKey);
            tCoFlowExecuteDefinition.setTaskId(taskId);
            tCoFlowExecuteDefinition.setHandler(handler);
            tCoFlowExecuteDefinition.setHandleOpt(handleOpt);
            tCoFlowExecuteDefinition.setRemark(handleRemark);
            tCoFlowExecuteDefinition.setNextTaskKey(nextNodeId);
            tCoFlowExecuteDefinition.setFormKey(formKey);
            tCoFlowExecuteDefinition.setHandleTime(PubFun.getSysDate());
            try {
                handledCurrentTask(tCoFlowExecuteDefinition);
            } catch (RbackException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handledCurrentTask(TCoFlowExecuteDefinition tCoFlowExecuteDefinition) throws RbackException {
        flowCoreMapper.handledCurrentTask(tCoFlowExecuteDefinition);
    }
}
