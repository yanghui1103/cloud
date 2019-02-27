package com.bw.fit.component.flow.mapper;

import com.bw.fit.component.flow.entity.TCoFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowExecuteDefinition;
import com.bw.fit.component.flow.entity.TFlowRegister;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * 流程Plus
 */
@Mapper
public interface FlowPlusMapper {
    /*****
     * 查看此实例流转情况，按照时间倒序
     * @param processId
     * @return
     */
    List<TCoFlowExecuteDefinition> getFlowCoExecutions(String processId);

    /*****
     * 根据流程定义Key，节点任务key及顺序值查到其前面的节点，并按照顺序值倒序列出
     * @param tFlowExecuteDefinition
     * @return
     */
    List<TFlowExecuteDefinition> getBeforeNodeByCurtNode(TFlowExecuteDefinition tFlowExecuteDefinition);

    /*****
     * 根据流程定义key，节点key获取此处定义详情
     * @param tFlowExecuteDefinition
     * @return
     */
    TFlowExecuteDefinition getThisNode(TFlowExecuteDefinition tFlowExecuteDefinition);

    /****
     * 获取所有定义
     * @param tFlowExecuteDefinition
     * @return
     */
    List<TFlowExecuteDefinition> getAllFlowDefs(TFlowExecuteDefinition tFlowExecuteDefinition);

    /****
     * 流程实例id，查询出登记情况
     * @param flowId
     * @return
     */
    List<TFlowRegister> getFlowRegsByFlowId(String flowId);

}
