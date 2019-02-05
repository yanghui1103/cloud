package com.bw.fit.pc.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 12:43
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private Environment env;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public JSONObject setCacheValue(String key, JSONObject jsonObject) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("key", key);
        params.add("value", jsonObject.toJSONString());
        ResponseEntity<JSONObject> response =
                restTemplate.postForEntity("http://cache-proj/cache/cache", params, JSONObject.class);
        JSONObject json = (JSONObject)JSONObject.parse(response.getBody().toJSONString());
        return json;
    }

    @Override
    public String getCacheValue(String key) {
        String response =
                restTemplate.getForObject("http://cache-proj/cache/key/" + key,  String.class);
        return response;
    }

    @Override
    public JSONObject deleteCache(String key) {
        JSONObject jsonObject = new JSONObject();
        restTemplate.delete("http://cache-proj/cache/cache/" + key);
        String response =
                restTemplate.getForObject("http://cache-proj/cache/getString/" + key,  String.class);
        if(response == null || "".equals(response)){
            PubFun.returnFailJson(jsonObject,"缓存删除失败");
        }else{
            PubFun.returnSuccessJson(jsonObject);
        }
        return jsonObject;
    }

    @Override
    public void expireKey(String key, int sencods) {
        restTemplate.postForEntity("http://cache-proj/cache/expire/"+key+"/"+sencods, null, JSONObject.class);
    }

    @Override
    public String getOtherAppReturnString(String url, Map<String, Object> params) {
        ResponseEntity<String> response = restTemplate.getForEntity(url,
                String.class ,params);
        return  response.getBody()  ;
    }

    @Override
    public JSONObject getOtherAppReturn(String url) {
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url,
                JSONObject.class );
        return  response.getBody()  ;
    }

    @Override
    public String getOtherAppJSONAarry(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url,
                String.class );
        return  response.getBody()  ;
    }

    @Override
    public JSONObject getOtherAppReturn(String url, MultiValueMap<String, Object> params) {
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url,
                JSONObject.class,params );
        return  response.getBody()  ;
    }

    @Override
    public JSONObject postOtherAppReturn(String url,MultiValueMap<String, Object> params) {
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(url,params,
                JSONObject.class );
        return  response.getBody()  ;
    }

    @Override
    public JSONObject putOtherAppReturn(String url, HttpEntity<String> entity) {
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.PUT, entity, JSONObject.class);
        return  response.getBody()  ;
    }

    @Override
    public JSONObject deleteOtherAppReturn(String url,Object[] args) {
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.PUT, null, JSONObject.class,args);
        return response.getBody()  ;
    }

    @Override
    public JSONObject getAccount(String sessionId) {
        String string = getCacheValue("session:"+sessionId);
        if(StrUtil.isNotEmpty(string)){
            JSONObject accountJSON =  JSONObject.parseObject(string);
            accountJSON.put("res","2");
            return accountJSON;
        }else{
            JSONObject accountJSON = new JSONObject();
            accountJSON.put("res","1");
            accountJSON.put("msg","无数据");
            return accountJSON;
        }
    }
}
