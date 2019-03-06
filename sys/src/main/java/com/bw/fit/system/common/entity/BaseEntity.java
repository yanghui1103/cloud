package com.bw.fit.system.common.entity;

import java.util.Date;
import java.util.List;
/*****
 * 父实体类
 * @author yangh
 *
 */
public class BaseEntity {
	public String tenant;
	public String id ;
	public String parentId;
	public String xaId;
	public String foreignId;
	public String keyWords="";	
	public String start_date="1900-01-01" ;
	public String end_date="2099-12-31" ;
	public String createTime ;
	public String versionTime ;
	public String operator;
	public String creator;
	public String creatorName; 
	public String createOrgId;
	public String createOrgName;
	public String logId ;
	public String logContent;
	public String sql ;
	public String isdeleted;
	public String res ;
	public String msg ;
	public String returnInfo ;
	public String staff_id;
	public String action_name ;
	public String UUID ;
	public String status ;
	public int sortNumber;
	public String tempStr;
	public String tempStr1;
	public String tempStr2;
	public String tempStr3;
	public String tempStr4;
	public String tempStr5;
	public String tempStr6;
	public String tempStr7;
	public String tempStr8;
	public String tempStr9;
	public List<String> tempList;
	public List<String> tempList1;
	public List<String> tempList2;
	public List<String> tempList3;
	public String remark;
	/***
	 * 翻页使用
	 */
	public Integer page ;
	public Integer rows ;
	public String paginationEnable;
	public Integer rn ;
	public List<String> createOrgIds;
	public String sessionId ;


	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getXaId() {
		return xaId;
	}

	public void setXaId(String xaId) {
		this.xaId = xaId;
	}

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public String getTempStr() {
		return tempStr;
	}

	public void setTempStr(String tempStr) {
		this.tempStr = tempStr;
	}

	public String getTempStr1() {
		return tempStr1;
	}

	public void setTempStr1(String tempStr1) {
		this.tempStr1 = tempStr1;
	}

	public String getTempStr2() {
		return tempStr2;
	}

	public void setTempStr2(String tempStr2) {
		this.tempStr2 = tempStr2;
	}

	public String getTempStr3() {
		return tempStr3;
	}

	public void setTempStr3(String tempStr3) {
		this.tempStr3 = tempStr3;
	}

	public String getTempStr4() {
		return tempStr4;
	}

	public void setTempStr4(String tempStr4) {
		this.tempStr4 = tempStr4;
	}

	public String getTempStr5() {
		return tempStr5;
	}

	public void setTempStr5(String tempStr5) {
		this.tempStr5 = tempStr5;
	}

	public String getTempStr6() {
		return tempStr6;
	}

	public void setTempStr6(String tempStr6) {
		this.tempStr6 = tempStr6;
	}

	public String getTempStr7() {
		return tempStr7;
	}

	public void setTempStr7(String tempStr7) {
		this.tempStr7 = tempStr7;
	}

	public String getTempStr8() {
		return tempStr8;
	}

	public void setTempStr8(String tempStr8) {
		this.tempStr8 = tempStr8;
	}

	public String getTempStr9() {
		return tempStr9;
	}

	public void setTempStr9(String tempStr9) {
		this.tempStr9 = tempStr9;
	}

	public List<String> getTempList() {
		return tempList;
	}

	public void setTempList(List<String> tempList) {
		this.tempList = tempList;
	}

	public List<String> getTempList1() {
		return tempList1;
	}

	public void setTempList1(List<String> tempList1) {
		this.tempList1 = tempList1;
	}

	public List<String> getTempList2() {
		return tempList2;
	}

	public void setTempList2(List<String> tempList2) {
		this.tempList2 = tempList2;
	}

	public List<String> getTempList3() {
		return tempList3;
	}

	public void setTempList3(List<String> tempList3) {
		this.tempList3 = tempList3;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getPaginationEnable() {
		return paginationEnable;
	}

	public void setPaginationEnable(String paginationEnable) {
		this.paginationEnable = paginationEnable;
	}

	public Integer getRn() {
		return rn;
	}

	public void setRn(Integer rn) {
		this.rn = rn;
	}

	public List<String> getCreateOrgIds() {
		return createOrgIds;
	}

	public void setCreateOrgIds(List<String> createOrgIds) {
		this.createOrgIds = createOrgIds;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
