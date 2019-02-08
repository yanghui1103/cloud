package com.bw.fit.component.flow.listener;

import org.activiti.engine.delegate.DelegateTask;

/**
 * @Description 每个需要系统用户任务办理的节点统一加入此监听，在create阶段就加入；
 *              支持Delegate expression
 * @Author yangh
 * @Date 2019-2-3 9:21
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface CommonSetCandidateUsersListener {

    /*****
     * 动态安排节点处理人
     * @param taskDefKey
     * @param processDefKey
     * @param delegateTask
     */
    void setCandidateUsersetCandidateUsers(String taskDefKey, String processDefKey,String processId, DelegateTask delegateTask);
}
