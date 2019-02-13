package com.bw.fit.rpt.common.controller;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("rpt")
@Controller
@EnableEurekaClient
public class RptController {

    @GetMapping("test")
    @ResponseBody
    public String test(@RequestParam(name = "name",required = false) String name){
        System.out.println("name:"+name);
        return "1223322321";
    }
}
