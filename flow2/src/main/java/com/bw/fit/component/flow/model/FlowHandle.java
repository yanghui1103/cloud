package com.bw.fit.component.flow.model;

import com.bw.fit.component.form.model.BaseModel;

/*****
 * 流程处理模型
 */
public class FlowHandle extends BaseModel {

    private String handleOpt;
    private String toTaskDefKey;
    private String toHandler;
    private String taskId;
    private String pInstanceId;

    public String getHandleOpt() {
        return handleOpt;
    }

    public void setHandleOpt(String handleOpt) {
        this.handleOpt = handleOpt;
    }

    public String getToTaskDefKey() {
        return toTaskDefKey;
    }

    public void setToTaskDefKey(String toTaskDefKey) {
        this.toTaskDefKey = toTaskDefKey;
    }

    public String getToHandler() {
        return toHandler;
    }

    public void setToHandler(String toHandler) {
        this.toHandler = toHandler;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getpInstanceId() {
        return pInstanceId;
    }

    public void setpInstanceId(String pInstanceId) {
        this.pInstanceId = pInstanceId;
    }
}
