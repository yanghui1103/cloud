package com.bw.fit.system.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.dao.AccountDao;
import com.bw.fit.system.account.entity.TAccount;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.common.util.PubFun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 19:12
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Controller
@RequestMapping(value="account")
@EnableEurekaClient
public class AccountController extends BaseController {

    @Resource
    private AccountDao accountDao;

    @GetMapping (value="account/{logName}")
    @ResponseBody
    public JSONObject get(@PathVariable(value = "logName") String logName){
        TAccount tAccount = new TAccount();
        tAccount.setLogName(logName);
        TAccount tAccount1 = accountDao.getByLogName(tAccount);
        return (JSONObject)JSONObject.toJSON(tAccount1);
    }

}
