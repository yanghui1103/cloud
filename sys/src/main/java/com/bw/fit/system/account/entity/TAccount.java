package com.bw.fit.system.account.entity;

import com.bw.fit.system.common.entity.BaseEntity;
import com.bw.fit.system.user.entity.TUser;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:09
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class TAccount extends TUser {

    private String logName;
    private String logPwd;
    private String userId;

    public String getLogName() {
        return (logName!=null)?logName.toLowerCase():"";
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
}
