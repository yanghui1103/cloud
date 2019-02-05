package com.bw.fit.sys.account.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@EnableEurekaClient
public class AccountController {
    @Autowired
    private CommonService commonService;

    @GetMapping("account/menus/{sessionId}")
    @ResponseBody
    public JSONArray menus(@PathVariable String sessionId){
        Map<String,Object> map = new HashMap<>();
        map.put("sessionId",sessionId);
        String string =commonService.getOtherAppReturnString("http://sys-proj/account/menus/"+sessionId,map);
        return JSONArray.parseArray(string);
    }
}
