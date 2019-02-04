package com.bw.fit.component.form.model;

import javax.validation.constraints.NotEmpty;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:42
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class Form extends  BaseModel{

    @NotEmpty(message = "表单Key不许为空")
    private String formKey;
    private String htmlName;
    private String name ;
    private String attr;
    @NotEmpty(message = "属性类型不许为空")
    private String attrType;
    @NotEmpty(message = "归属Tab不许为空")
    private String tabName;
    private int tabOrder;


    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getTabOrder() {
        return tabOrder;
    }

    public void setTabOrder(int tabOrder) {
        this.tabOrder = tabOrder;
    }
}
