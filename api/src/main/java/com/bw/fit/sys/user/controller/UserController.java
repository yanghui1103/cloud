package com.bw.fit.sys.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("user")
@Controller
@EnableEurekaClient
public class UserController  {
    @Autowired
    private CommonService commonService;

    @GetMapping("openDetail/{id}")
    public  String openDetail(@PathVariable String id, Model model){
        Session session = PubFun.getCurrentSession();
        JSONObject jsonObject = commonService.getOtherAppReturn("http://sys-proj/user/user/"+id+"/"+session.getId());
        Map mapObj = JSONObject.toJavaObject(jsonObject, Map.class);
        model.addAttribute("user",mapObj);
        return  "sys/pc/system/user/userDetail";
    }

}
