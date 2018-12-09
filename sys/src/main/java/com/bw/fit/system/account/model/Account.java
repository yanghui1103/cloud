package com.bw.fit.system.account.model;

import com.bw.fit.system.common.model.BaseModel;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:14
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class Account extends BaseModel {


    private String logName;
    private String logPwd;

    public String getLogPwd() {
        return logPwd;
    }

    public void setLogPwd(String logPwd) {
        this.logPwd = logPwd;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }
}
