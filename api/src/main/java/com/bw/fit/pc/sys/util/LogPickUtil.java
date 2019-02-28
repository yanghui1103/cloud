package com.bw.fit.pc.sys.util;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 日志收集工具类
 * @Author yangh
 * @Date 2019-2-27 11:01
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
public class LogPickUtil {
    @Resource
    private RestTemplateUtil restTemplateUtil;

    /*****
     * 日志收集
     * @param httpServletRequest
     * @param targetMsUrl 目标微服务的地址
     * @param operateFunction 对应使用微服务的controller接口方法
     * @param resourceId   资源id
     * @param formReqString  表单拼接的字符串
     */
    public void  collect(HttpServletRequest httpServletRequest,String targetMsUrl, String operateFunction, String resourceId, String formReqString,String result){
        try {
            MultiValueMap<String, Object> mapForLog = new LinkedMultiValueMap<String, Object>();
            mapForLog.add("sessionId", PubFun.getCurrentSessionId());
            mapForLog.add("creator",PubFun.getCurrentUser().getString("id"));
            mapForLog.add("ip",PubFun.getIpAddr(httpServletRequest));
            mapForLog.add("url",targetMsUrl);
            mapForLog.add("logType",httpServletRequest.getMethod());
            mapForLog.add("operateFunction",operateFunction);
            mapForLog.add("resourceId",resourceId);
            mapForLog.add("params",formReqString);
            mapForLog.add("result",result.replace("{","").replace("}",""));
            String string = restTemplateUtil.post(httpServletRequest,"http://common-proj/log/log?creator="+PubFun.getCurrentUser().getString("id")+"&ip="+PubFun.getIpAddr(httpServletRequest)+"&url="+targetMsUrl+"&logType="+httpServletRequest.getMethod()+"&operateFunction="+operateFunction+"&resourceId="+resourceId
                    +"&params="+formReqString+"&result="+result.replace("{","").replace("}",""),
                    mapForLog);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
