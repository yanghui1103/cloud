package com.bw.fit.component.flow.service;

import java.util.List;
import java.util.Map;

import com.bw.fit.component.flow.entity.TCoFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowRegister;
import com.bw.fit.component.flow.model.RbackException;
import org.activiti.engine.runtime.ProcessInstance;

import com.alibaba.fastjson.JSONObject;

/*****
 * 流程Plus组件
 * @author yangh
 *
 */
public interface FlowPlusService {
    /*****
     * 查看此实例流转情况，按照时间倒序
     * @param processId
     * @return
     */
    List<TCoFlowExecuteDefinition> getFlowCoExecutions(String processId);

    /****
     * 查看流程实例当前所在节点可以驳回的节点定义
     * @param processId
     * @return
     */
    List<TFlowExecuteDefinition> getCanBackFlowNodes(String processId);

    /****
     * 流程登记
     * @param tFlowRegister
     * @return
     * @throws RbackException
     */
    JSONObject createRegisterPInstance(TFlowRegister tFlowRegister) throws RbackException;
}
