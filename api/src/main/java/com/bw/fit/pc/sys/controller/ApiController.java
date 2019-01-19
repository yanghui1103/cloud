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
        return   url!=null?url:"";
    }

    @GetMapping(value="getMicroServiceResult/v1/{argsNum}/{serviceName}/{controllerName}/{param1}/{param2}/{param3}/{param4}/{param5}/{param6}/{param7}/{param8}/{param9}")
    @ResponseBody
    public JSONObject getMicroServiceResultV1(@PathVariable String serviceName,@PathVariable String controllerName,
                                            @PathVariable String param1,@PathVariable String param2,
                                            @PathVariable String param3,@PathVariable String param4,
                                            @PathVariable String param5,@PathVariable String param6,
                                            @PathVariable String param7,@PathVariable String param8,@PathVariable String param9,@PathVariable int argsNum ){
        JSONObject jsonObject = new JSONObject();
        if(argsNum == 1){
            jsonObject = restTemplate.getForObject("http://"+serviceName+"/"+controllerName+"/"+param1 , JSONObject.class);
        }else if(argsNum == 2) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2, JSONObject.class);
        }else if(argsNum == 3) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2+ "/" + param3, JSONObject.class);
        }else if(argsNum == 4) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2+ "/" + param3+ "/" + param4, JSONObject.class);
        }else if(argsNum == 5) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2+ "/" + param3+ "/" + param4+ "/" + param5, JSONObject.class);
        }else if(argsNum == 6) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2+ "/" + param3+ "/" + param4+ "/" + param5+ "/" + param6, JSONObject.class);
        }else if(argsNum == 7) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2+ "/" + param3+ "/" + param4+ "/" + param5+ "/" + param6+ "/" + param7, JSONObject.class);
        }else if(argsNum == 8) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2+ "/" + param3+ "/" + param4+ "/" + param5+ "/" + param6+ "/" + param7+ "/" + param8, JSONObject.class);
        }else if(argsNum == 9) {
            jsonObject = restTemplate.getForObject("http://" + serviceName + "/" + controllerName + "/" + param1 + "/" + param2+ "/" + param3+ "/" + param4+ "/" + param5+ "/" + param6+ "/" + param7+ "/" + param8+ "/" + param9, JSONObject.class);
        }else{
            PubFun.returnFailJson(jsonObject,"抱歉，系统为提供9位以上参数方法");
        }
        return jsonObject;
    }

}
