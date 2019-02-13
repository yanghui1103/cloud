package com.bw.fit.sys.account.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import com.bw.fit.pc.sys.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@EnableEurekaClient
public class AccountController {
    @Autowired
    private CommonService commonService;
    @Resource
    private RestTemplateUtil restTemplateUtil;

    @GetMapping("account/menus")
    @ResponseBody
    public JSONArray menus( HttpServletRequest request){
        String string = restTemplateUtil.get(request,"http://sys-proj/account/menus" ,null);
        //String string =commonService.getOtherAppReturnString("http://sys-proj/account/menus" ,map);
        return JSONArray.parseArray(string);
    }
}
