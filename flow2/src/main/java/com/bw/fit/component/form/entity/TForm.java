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
    private String attr;
    private String tabType;
    private String tabName;
    private int tabOrder;


    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getTabType() {
        return tabType;
    }

    public void setTabType(String tabType) {
        this.tabType = tabType;
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

