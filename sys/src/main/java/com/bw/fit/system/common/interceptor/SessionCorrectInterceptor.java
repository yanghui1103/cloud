package com.bw.fit.system.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 验证请求中sessionId关联的账户信息是否还有效
 * @Author yangh
 * @Date 2019-1-30 15:19
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public class SessionCorrectInterceptor  implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(SessionCorrectInterceptor.class);
    @Autowired
    private  CommonService commonService;
    long start = System.currentTimeMillis();
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String sessionId = httpServletRequest.getParameter("sessionId");
        if(StrUtil.isNotEmpty(sessionId)){
            String s = commonService.getCacheValue(sessionId);
            if(StrUtil.isNotEmpty(s)){
                JSONObject account = JSONObject.parseObject(s);
            }else{
                return  false; // 说明没有从缓存库里找到有效账户数据
            }
        }
        start = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info(httpServletRequest.getRequestURL()+",耗时="+(System.currentTimeMillis()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
