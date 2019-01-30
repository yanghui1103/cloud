package com.bw.fit.pc.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
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
        return  url!=null?url:"";
    }

    @GetMapping(value="getMicroServiceResult/v1/{serviceName}/{controllerName}/{params}")
    @ResponseBody
    public JSONObject getMicroServiceResultV1(@PathVariable String serviceName,@PathVariable String controllerName,
                                            @PathVariable String params   ){
        JSONObject jsonObject = new JSONObject();
        String[] paramArray = params.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        if(paramArray !=null ){
            for(int i=0;i<paramArray.length;i++){
                stringBuffer.append(paramArray[i]);
                if(i!=paramArray.length-1){
                    stringBuffer.append("/");
                }
            }
            jsonObject = restTemplate.getForObject("http://"+serviceName+"/"+controllerName+"/"+stringBuffer.toString() , JSONObject.class);
        }else{
            PubFun.returnFailJson(jsonObject,"抱歉，系统尚未提供无参数方法");
        }
        return jsonObject;
    }

}
