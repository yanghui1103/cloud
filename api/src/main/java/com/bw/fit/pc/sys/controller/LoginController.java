package com.bw.fit.pc.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@EnableEurekaClient
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("test")
    @ResponseBody
    public String test(){
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("account","w33");
        String result = restTemplate.getForObject("https://www.2cto.com/ask/question/6876",
                String.class );
        return  result  ;

    }
}
