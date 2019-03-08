package com.bw.fit.component.flow.entity;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 10:18
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class TCoFlowExecuteDefinition extends BaseEntity {

    private  String processDefKey;
    private  String processId ;
    private  String taskDefKey;
    private String taskId ;
    private String handler;
    private String handleTime;
    private String handleOpt;
    private String nextTaskKey;
    private String formKey;
    private String handlers;

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getHandleOpt() {
        return handleOpt;
    }

    public void setHandleOpt(String handleOpt) {
        this.handleOpt = handleOpt;
    }

    public String getNextTaskKey() {
        return nextTaskKey;
    }

    public void setNextTaskKey(String nextTaskKey) {
        this.nextTaskKey = nextTaskKey;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getHandlers() {
        return handlers;
    }

    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }
}
