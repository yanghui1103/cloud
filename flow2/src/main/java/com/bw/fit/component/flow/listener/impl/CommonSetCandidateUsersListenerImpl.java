package com.bw.fit.component.flow.listener.impl;

import cn.hutool.core.util.ObjectUtil;
import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;
import com.bw.fit.component.flow.listener.CommonSetCandidateUsersListener;
import com.bw.fit.component.flow.mapper.FlowCoreMapper;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-3 9:22
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service(value="commonSetCandidateUsersListener")
public class CommonSetCandidateUsersListenerImpl implements TaskListener, CommonSetCandidateUsersListener {
    private  static Logger logger = LoggerFactory.getLogger(CommonSetCandidateUsersListenerImpl.class);
    @Resource
    private FlowCoreMapper flowCoreMapper;

    @Override
    public void notify(DelegateTask delegateTask) {
        if("create".equalsIgnoreCase(delegateTask.getEventName())){
            String taskDefKey = delegateTask.getTaskDefinitionKey();
            String processDefKey = delegateTask.getProcessDefinitionId();
            String processId = delegateTask.getProcessInstanceId();
            setCandidateUsersetCandidateUsers(taskDefKey,processDefKey,processId,delegateTask);
        }
    }

    @Override
    public void setCandidateUsersetCandidateUsers(String taskDefKey,String processDefKey,String processId,DelegateTask delegateTask) {
        TFlowExecuteDefinition tFlowExecuteDefinition = new TFlowExecuteDefinition();
        tFlowExecuteDefinition.setTaskDefKey(taskDefKey);
        tFlowExecuteDefinition.setProcessDefKey(processDefKey);
        tFlowExecuteDefinition.setProcessId(processId);
        List<TFlowExecuteDefinition> tfs = flowCoreMapper.getNowHandlers(tFlowExecuteDefinition);
        if(ObjectUtil.isNotNull(tfs) && tfs.size()>0){ // 如果之前有定义,就安排处理人
            List<String> users = new ArrayList<>();
            for(TFlowExecuteDefinition tf:tfs){
                users.addAll(Arrays.asList(tf.getHandlers().split(",")));
            }
            delegateTask.addCandidateUsers(users);
        }else{
            logger.info("ERROR:安置节点处理人监听器没有发现流程节点："+taskDefKey+",有处理人");
        }

    }
}
