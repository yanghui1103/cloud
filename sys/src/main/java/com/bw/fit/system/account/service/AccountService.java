package com.bw.fit.system.account.service;

import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.common.model.BaseModel;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:09
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface AccountService {
    Account get(String logName, BaseModel baseModel);

}
