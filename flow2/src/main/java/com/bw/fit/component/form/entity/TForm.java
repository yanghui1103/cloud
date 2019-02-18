package com.bw.fit.component.form.entity;

import com.bw.fit.component.flow.entity.BaseEntity;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:38
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class TForm extends BaseEntity {

    private String formKey;
    private String htmlName;
    private String name ;
    private String attr;
    private String attrType;
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

