package com.bw.fit.system.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.mapper.AccountMapper;
import com.bw.fit.system.account.entity.TAccount;
import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.common.util.PubFun;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    RestTemplate restTemplate;
    @Resource
    private AccountMapper accountMapper;

    @GetMapping (value="account/{logName}")
    @ResponseBody
    public JSONObject get(@PathVariable(value = "logName") String logName){
        TAccount tAccount = new TAccount();
        tAccount.setLogName(logName);
        TAccount tAccount1 = accountMapper.getByLogName(tAccount);
        return (JSONObject)JSONObject.toJSON(tAccount1);

    }

    @GetMapping(value="menus")
    @ResponseBody
    public JSONObject menus(){
        JSONObject jsonObject = new JSONObject();
        String s = restTemplate.getForObject("http://cache-proj/cache/cache/key/key", String.class);
        System.out.println(s+"ssss");
        return jsonObject;
    }

}
