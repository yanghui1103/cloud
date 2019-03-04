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
    private Map<String,List<String>> attachmentForm;
    private Map<String,List<Map<String,String>>> kvForm;
    private Map<String,List<String>> listForm;

    public Map<String, List<String>> getAttachmentForm() {
        return attachmentForm;
    }

    public void setAttachmentForm(Map<String, List<String>> attachmentForm) {
        this.attachmentForm = attachmentForm;
    }

    public Map<String, List<Map<String, String>>> getKvForm() {
        return kvForm;
    }

    public void setKvForm(Map<String, List<Map<String, String>>> kvForm) {
        this.kvForm = kvForm;
    }

    public Map<String, List<String>> getListForm() {
        return listForm;
    }

    public void setListForm(Map<String, List<String>> listForm) {
        this.listForm = listForm;
    }
}
