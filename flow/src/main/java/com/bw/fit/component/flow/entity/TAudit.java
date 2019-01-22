package com.bw.fit.component.flow.entity;

import com.bw.fit.system.common.entity.BaseEntity;
/****
 * 工作流操作
 * @author yangh
 *
 */
public class TAudit extends BaseEntity {

	private String auditOpt;
	private String pdName;
	private String formKey;
	private String taskId;
	
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getAuditOpt() {
		return auditOpt;
	}
	public void setAuditOpt(String auditOpt) {
		this.auditOpt = auditOpt;
	}
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	
}
