package com.bw.fit.component.flow.entity;

import com.bw.fit.system.common.entity.BaseEntity;

/*****
 * 待办/待阅实体
 * @author yangh
 *
 */
public class TTodo extends BaseEntity {
	private String title;
	private String path;
	private String modelId;
	private String modelName;
	private String appName;
	private String appId;
	private String typeCode;
	private String accountId;
	private String taskId;
	private String auditOpt;
	
	
	
	
	public String getAuditOpt() {
		return auditOpt;
	}
	public void setAuditOpt(String auditOpt) {
		this.auditOpt = auditOpt;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	
}
