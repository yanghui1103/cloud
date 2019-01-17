package com.bw.fit.pc.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
                restTemplate.postForEntity(env.getProperty("zuul.routes.api-cache.url")+"/cache/cache", params, JSONObject.class);
        JSONObject json = (JSONObject)JSONObject.parse(response.getBody().toJSONString());
        return json;
    }

    @Override
    public String getCacheValue(String key) {
        String response =
                restTemplate.getForObject(env.getProperty("zuul.routes.api-cache.url")+"/cache/getString/" + key,  String.class);
        return response;
    }

    @Override
    public JSONObject deleteCache(String key) {
        JSONObject jsonObject = new JSONObject();
        restTemplate.delete(env.getProperty("zuul.routes.api-cache.url")+"/cache/cache/" + key);
        String response =
                restTemplate.getForObject(env.getProperty("zuul.routes.api-cache.url")+"/cache/getString/" + key,  String.class);
        if(response == null || "".equals(response)){
            PubFun.returnFailJson(jsonObject,"缓存删除失败");
        }else{
            PubFun.returnSuccessJson(jsonObject);
        }
        return jsonObject;
    }

    @Override
    public void expireKey(String key, int sencods) {
        restTemplate.postForEntity(env.getProperty("zuul.routes.api-cache.url")+"/cache/expire/"+key+"/"+sencods, null, JSONObject.class);
    }

    @Override
    public JSONObject getOtherAppReturn(String url) {
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url,
                JSONObject.class );
        return  response.getBody()  ;
    }

}
