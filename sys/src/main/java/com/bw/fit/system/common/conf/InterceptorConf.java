package com.bw.fit.system.common.conf;

import com.bw.fit.system.common.interceptor.SessionCorrectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description
 * @Author yangh
 * @Date 2019-1-30 15:13
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Configuration
public class InterceptorConf extends WebMvcConfigurerAdapter  {
    @Bean
    SessionCorrectInterceptor getSessionCorrectInterceptor(){
        return new SessionCorrectInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionCorrectInterceptor())
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
