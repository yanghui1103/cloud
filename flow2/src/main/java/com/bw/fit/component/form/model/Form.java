package com.bw.fit.component.form.model;

import java.util.List;
import java.util.Map;

/**
 * @Description 单笔传入的表单数据模型
 * @Author yangh
 * @Date 2019-3-4 14:49
 * @VERSION
 */
public class Form extends BaseModel {
    private Map<String,String> attachmentForm;  // key为tab类型:num:tab的名称,value例如att001, att002
    private Map<String,String> kvForm;       // key为tab类型:num:tab的名称,value例如姓名:张w,年龄:45,性别:女
    private Map<String,String> listForm; // key为tab类型:num:tab的名称,value例如支付宝:天猫:dingding0, 支付宝:天猫:dingding1, 支付宝:天猫:dingding2, 支付宝:;天猫:dingding3, 支付宝:天猫:dingding4

    public Map<String, String> getAttachmentForm() {
        return attachmentForm;
    }

    public void setAttachmentForm(Map<String, String> attachmentForm) {
        this.attachmentForm = attachmentForm;
    }

    public Map<String, String> getKvForm() {
        return kvForm;
    }

    public void setKvForm(Map<String, String> kvForm) {
        this.kvForm = kvForm;
    }

    public Map<String, String> getListForm() {
        return listForm;
    }

    public void setListForm(Map<String, String> listForm) {
        this.listForm = listForm;
    }
}
