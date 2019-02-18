package com.bw.fit.component.flow.listener;

import com.bw.fit.component.flow.entity.TCoFlowExecuteDefinition;
import com.bw.fit.component.flow.model.RbackException;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 9:32
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface HandledEventListener {

    /*****
     * 节点任务办理完毕，触发完结
     * @param tCoFlowExecuteDefinition
     */
    void handledCurrentTask(TCoFlowExecuteDefinition tCoFlowExecuteDefinition) throws RbackException;
}
