package com.bw.fit.pc.sys.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/*****
 * 通用的rest工具类
 */
@Component
public class RestTemplateUtil {

    @Resource
    private RestTemplate restTemplate;

    public String post(ServletRequest req, String url, MultiValueMap<String, ?> params) {
        ResponseEntity<String> rss = request(req, url, HttpMethod.POST, params);
        return rss.getBody();
    }

    /*****
     * 通过url远程调用微服务的查询接口
     * @param req
     * @param url
     * @param params
     * @return
     */
    public String get(ServletRequest req, String url, MultiValueMap<String, ?> params) {
        ResponseEntity<String> rss = request(req, url, HttpMethod.GET, params);
        return rss.getBody();
    }

    public String getByForm(ServletRequest req, String url, MultiValueMap<String, ?> params) {
        ResponseEntity<String> rss = requestv2(req, url, HttpMethod.GET, params);
        return rss.getBody();
    }

    public String delete(ServletRequest req, String url, MultiValueMap<String, ?> params) {
        ResponseEntity<String> rss = request(req, url, HttpMethod.DELETE, params);
        return rss.getBody();
    }

    public String put(ServletRequest req, String url, MultiValueMap<String, ?> params) {
        ResponseEntity<String> rss = request(req, url, HttpMethod.PUT, params);
        return rss.getBody();
    }

    /**
     * @param req
     * @param url
     * @param method
     * @param params maybe null
     * @return
     */
    private ResponseEntity<String> request(ServletRequest req, String url, HttpMethod method, MultiValueMap<String, ?> params) {
        HttpServletRequest request = (HttpServletRequest) req;
        //获取header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeaders.add(key, value);
        }
        //获取parameter信息
        if(params == null) {
            params = new LinkedMultiValueMap<>();
            Set<String> keySet = request.getParameterMap().keySet();
            for (String key : keySet) {
                String[] values = request.getParameterMap().get(key);
                for(String value:values){
                    ((LinkedMultiValueMap)params).add(key,value);
                }
            }
        }
        Optional ops = requestHeaders.keySet().stream().filter(x->"sessionId".equalsIgnoreCase(x)).findAny();
        if(!ops.isPresent()){
            List<String> sessions = (List<String>) params.get("sessionId");
            requestHeaders.add("sessionId", sessions==null?"":sessions.get(0));
        }

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> rss = restTemplate.exchange(url, method, requestEntity, String.class, params);
        return rss;
    }

    /**
     * 功能描述: 前端传入form表单，后端使用model接收的查询条件方式进行远程查询
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2019-3-1 9:48
     */
    private ResponseEntity<String> requestv2(ServletRequest req, String url, HttpMethod method, MultiValueMap<String, ?> params) {
        HttpServletRequest request = (HttpServletRequest) req;
        //获取header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeaders.add(key, value);
        }
        //获取parameter信息,并拼接request串formString
        StringBuffer formString =  new StringBuffer();
        if(params == null || params!=null) {
            params = new LinkedMultiValueMap<>();
            Map<String,String[]> maps = request.getParameterMap() ;
            if(CollectionUtil.isNotEmpty(maps)){
                Set<String> keySet = maps.keySet();
                for (String key : keySet) {
                    String[] values = request.getParameterMap().get(key);
                    for(String value:values){
                        ((LinkedMultiValueMap)params).add(key,value);
                        formString.append("&"+key +"="+value);
                    }
                }
            }
        }
        Optional ops = requestHeaders.keySet().stream().filter(x->"sessionId".equalsIgnoreCase(x)).findAny();
        if(!ops.isPresent()){
            List<String> sessions = (List<String>) params.get("sessionId");
            requestHeaders.add("sessionId", sessions==null?"":sessions.get(0));
        }

        String formUrl = (!"".equals(formString.toString())?"?"+formString.substring(1,formString.length()):"");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> rss = restTemplate.exchange(url+formUrl, method, requestEntity, String.class, params);
        return rss;
    }

}
