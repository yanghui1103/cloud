package com.bw.fit.system.account.model;

import com.bw.fit.system.common.model.BaseModel;
import com.bw.fit.system.user.model.User;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:14
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class Account extends User {


    private String logName;
    private String logPwd;
    private String verificationCode;
    private String currentOrgId; /****归属组织id***/
    private String userId;
    private String positionIds;
    private String roleIds;

    public String getLogPwd() {
        return logPwd;
    }

    public void setLogPwd(String logPwd) {
        this.logPwd = logPwd;
    }

    public String getLogName() {
        return (logName!=null)?logName.toLowerCase():"";
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getCurrentOrgId() {
        return currentOrgId;
    }

    public void setCurrentOrgId(String currentOrgId) {
        this.currentOrgId = currentOrgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
