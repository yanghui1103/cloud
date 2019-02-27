package com.bw.fit.component.flow.model;

import com.bw.fit.component.flow.entity.TFlowRegister;

/****
 * 待办模型
 */
public class Todo extends TFlowRegister {

    String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
