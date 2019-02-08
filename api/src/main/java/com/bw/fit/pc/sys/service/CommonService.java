package com.bw.fit.pc.sys.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 12:43
 * @VERSION
 */
public interface CommonService {
    /**
     * 功能描述: 创建缓存数据
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-8 12:49
     */
    JSONObject setCacheValue(String key, JSONObject jsonObject);

    /**
     * 功能描述: 获取缓存库里的值
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-8 12:50
     */
    String getCacheValue(String key);

    /**
     * 功能描述: 删除缓存
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-8 19:20
     */
    JSONObject deleteCache(String key);
    /**
     * 功能描述: 设置key的有效时间
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-9 20:00
     */
    void expireKey(String key,int sencods);
    /**
     * 功能描述: 去请求别的应用的getMapping请求，
     *          仅支持携带参数的rest方式
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-9 20:00
     */
    public String getOtherAppReturnString(String url, Map<String, Object> params);
    /**
     * 功能描述: 去请求别的应用的getMapping请求，
     *          仅支持携带参数的rest方式
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-9 20:00
     */
    public Object getOtherAppReturnObject(String url, Map<String, Object> params);

    /**
     * 功能描述: 去请求别的应用的getMapping请求，
     *          仅支持携带参数的rest方式
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-9 20:00
     */
    public JSONObject getOtherAppReturn(String url);
    /**
     * 功能描述: 去请求别的应用的getMapping请求，
     *          仅支持携带参数的rest方式
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-9 20:00
     */
    public String getOtherAppJSONAarry(String url);
    /**
     * 功能描述: 去请求别的应用的getMapping请求
     *
     * @param: url
     * @param params 使用MultiValueMap数据结构，键可以重复
     * @return:
     * @auther: yangh
     * @date: 2018-12-9 20:00
     */
    public JSONObject getOtherAppReturn(String url,MultiValueMap<String, Object> params);
    /*****
     *  请求别的应用postMapping
     * @param url
     * @param params 使用MultiValueMap数据结构，键可以重复
     * @return
     */
    public JSONObject postOtherAppReturn(String url, MultiValueMap<String, Object> params);

    /*****
     * 请求别的应用putMapping
     * @param url
     * @param entity 例如：
     * String reqJsonStr = "{\"id\":227,\"code\":\"updateCC\", \"group\":\"UPDATE\",\"content\":\"updateCT\", \"order\":9}";
     * HttpHeaders headers = new HttpHeaders();
     * headers.setContentType(MediaType.APPLICATION_JSON);
     * HttpEntity<String> entity = new HttpEntity<String>(reqJsonStr,headers);
     * @return
     */
    public JSONObject putOtherAppReturn(String url, HttpEntity<String> entity);
    /*****
     * 请求别的应用的deleteMapping
     * @param url 例如http://member-service/student/{id}/{oid}
     * @param args 数据类型，可以按照上面{}次序放置数值
     * @return
     */
    public JSONObject deleteOtherAppReturn(String url,Object[] args);
    /****
     * 根据会话id查询缓存中的账户信息
     * @return
     */
    public JSONObject getAccount(String sessionId);
}
