package com.bw.fit.component.flow.entity;

/*****
 * 流程登记表
 */
public class TFlowRegister extends BaseEntity {
    String title;
    String flowId;
    String drafter;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getDrafter() {
        return drafter;
    }

    public void setDrafter(String drafter) {
        this.drafter = drafter;
    }
}
