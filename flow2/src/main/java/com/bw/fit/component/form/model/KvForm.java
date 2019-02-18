package com.bw.fit.component.form.model;

/*****
 * 针对兼值对表单
 */
public class KvForm extends BaseModel {
    private String name;
    private String attr;

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
}
