package com.bw.fit.innerzuul.common.controller;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequestMapping
@Controller
@EnableEurekaClient
public class LoginController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("login")
    public String login(@RequestParam(value="account",required = true) String account, @RequestParam(value="password",required = true)
                        String password, Model model){
        String errorPage="/common/base/login";
//        Map<String, Object> urlVariables = new HashMap<String, Object>();
//        urlVariables.put("account",account);
//        JSONObject result = restTemplate.getForObject("http://service-a/hi?id=",
//                JSONObject.class, urlVariables);
        return "forward:";

    }
}
