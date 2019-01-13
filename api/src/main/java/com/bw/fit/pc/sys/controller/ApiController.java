package com.bw.fit.pc.sys.controller;

import com.bw.fit.pc.sys.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@EnableEurekaClient
public class ApiController {
    private  static Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Autowired
    private CommonService commonService;

    @GetMapping("getMicroServiceUrl/{serviceName}")
    @ResponseBody
    public String getMicroServiceUrl(@PathVariable String serviceName){
        String url = env.getProperty("zuul.routes." + serviceName + ".url").toString();
        return   url!=null?url:"";
    }

}
