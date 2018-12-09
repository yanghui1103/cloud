package com.bw.fit.common.cache.controller;
import java.util.*;
import java.util.function.Predicate;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.common.cache.service.CacheService;
import com.bw.fit.common.cache.util.PubFun;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "cache")
@Controller
@EnableEurekaClient
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @GetMapping("getJSON/{key}")
    @ResponseBody
    public JSONObject get(@PathVariable String key){
        Object object = cacheService.get(key);
            return  (JSONObject)object;
    }

    @GetMapping("getString/{key}")
    @ResponseBody
    public String getStr(@PathVariable String key){
        Object object = cacheService.get(key);
        return  (String)object;
    }

    @PostMapping("setValue")
    @ResponseBody
    public JSONObject setValue(@RequestParam(value = "key",required = true) String key,
                               @RequestParam(value = "value",required = true) String value){
        JSONObject json = new JSONObject();
        cacheService.set(key,value);
        Object object = cacheService.get(key);
        if(object==null){
            PubFun.returnFailJson(json,"缓存操作失败,原因未知");
            return json;
        }
        PubFun.returnSuccessJson(json);
        return json;
    }

    @DeleteMapping("delete/{key}")
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "key",required = true) String key ){
        JSONObject json = new JSONObject();
        cacheService.delete(key);
        Object object = cacheService.get(key);
        if(object != null){
            PubFun.returnFailJson(json,"缓存操作失败,原因未知");
            return json;
        }
        PubFun.returnSuccessJson(json);
        return json;
    }

}
