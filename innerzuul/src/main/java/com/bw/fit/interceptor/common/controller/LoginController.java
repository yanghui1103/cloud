package com.bw.fit.interceptor.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
@EnableDiscoveryClient
public class LoginController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("login")
    @ResponseBody
    public String login(@RequestParam(value="account",required = true) String account, @RequestParam(value="password",required = true)
                        String password, Model model){
        String errorPage="/common/base/login";
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("account",account);
//        JSONObject result = restTemplate.getForObject("http://service-a/hi?id=",
//                JSONObject.class, urlVariables);
        //restTemplate.put("http://localhost:8001/sys/org/organizations",null);
        return "ok";
    }


    @RequestMapping("login2")
    @ResponseBody
    public String login2(){
        String errorPage="/common/base/login";
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("account","w33");
        String result = restTemplate.getForObject("http://api-sys:8001/sys/org/get/",
                String.class );
        return  result  ;
    }
}
