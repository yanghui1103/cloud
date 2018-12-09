package com.bw.fit.system.account.entity;

import com.bw.fit.system.common.entity.BaseEntity;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:09
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class TAccount extends BaseEntity {

    private String logName;
    private String logPwd;

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
}
