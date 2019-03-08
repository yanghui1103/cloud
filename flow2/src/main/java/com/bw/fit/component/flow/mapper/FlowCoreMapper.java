package com.bw.fit.component.flow.mapper;

import com.bw.fit.component.flow.entity.TCoFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;
import com.bw.fit.component.flow.model.RbackException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-3 11:48
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Mapper
public interface FlowCoreMapper {

    /*****
     * 根据流程定义，节点编码，查询出该节点安排的处理人（多人）账户id;以及定义详情
     * @param tFlowExecuteDefinition
     * @return TFlowExecuteDefinition
     */
    List<TFlowExecuteDefinition> getNowHandlers(TFlowExecuteDefinition tFlowExecuteDefinition);

    /*****
     * 获取定义详情
     * @param id
     * @return
     */
    TFlowExecuteDefinition getById(String id);

    /*****
     * 任务办结
     * @param tCoFlowExecuteDefinition
     */
    void handledCurrentTask(TCoFlowExecuteDefinition tCoFlowExecuteDefinition) throws RbackException;

    /*****
     * 根据流程定义key，流程实例id查询所有审核历史,及下一个节点处理的人
     * @param tCoFlowExecuteDefinition
     * @return
     */
    public List<TCoFlowExecuteDefinition> getFlowHandleHistorys(TCoFlowExecuteDefinition tCoFlowExecuteDefinition);
}
