package com.bw.fit.component.flow.mapper;

import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;

import java.util.List;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-3 11:48
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface FlowCoreMapper {

    /*****
     * 根据流程定义，节点编码，查询出该节点安排的处理人（多人）账户id;以及定义详情
     * @param tFlowExecuteDefinition
     * @return TFlowExecuteDefinition
     */
    TFlowExecuteDefinition getNowHandlers(TFlowExecuteDefinition tFlowExecuteDefinition);

    /*****
     * 获取定义详情
     * @param id
     * @return
     */
    TFlowExecuteDefinition getById(String id);
}
