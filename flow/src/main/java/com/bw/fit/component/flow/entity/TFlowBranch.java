package com.bw.fit.component.flow.entity;

import com.bw.fit.system.common.entity.BaseEntity;
/*****
 * 一个id，对应多个流程实例；主要适用于一个流程过来，可能满足多个分支，然后采取的这种方式。
 * @author yangh
 *
 */
public class TFlowBranch extends BaseEntity {

	private String piId;
	private String formKey;
	private String branch;
	
	
	
	public String getPiId() {
		return piId;
	}
	public void setPiId(String piId) {
		this.piId = piId;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
}
