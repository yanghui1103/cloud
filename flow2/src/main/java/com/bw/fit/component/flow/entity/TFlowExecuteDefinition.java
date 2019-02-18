package com.bw.fit.component.flow.entity;

/**
 * @Description 映射流程节点执行定义表
 * @Author yangh
 * @Date 2019-2-3 11:50
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */

public class TFlowExecuteDefinition extends BaseEntity{

    private String processDefKey;
    private String processId;
    private String taskDefKey;
    private int executeNum;
    private String relation;
    private String handlers;

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public int getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(int executeNum) {
        this.executeNum = executeNum;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getHandlers() {
        return handlers;
    }

    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
