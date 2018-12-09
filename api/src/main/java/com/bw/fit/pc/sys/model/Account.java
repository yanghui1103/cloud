package com.bw.fit.pc.sys.model;


public class Account extends BaseModel {
    private String logName;
    private String logPwd;
    private String verificationCode;
    private String userId;
    private String currentOrgId;


    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogPwd() {
        return logPwd;
    }

    public void setLogPwd(String logPwd) {
        this.logPwd = logPwd;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentOrgId() {
        return currentOrgId;
    }

    public void setCurrentOrgId(String currentOrgId) {
        this.currentOrgId = currentOrgId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
