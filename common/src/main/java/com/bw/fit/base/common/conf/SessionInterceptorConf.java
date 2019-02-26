package com.bw.fit.base.common.conf;

import com.bw.fit.base.common.interceptor.SessionCorrectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
 * @Date 2019-2-26 17:06
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Configuration
public class SessionInterceptorConf extends WebMvcConfigurerAdapter {
    @Bean
    SessionCorrectInterceptor getSessionCorrectInterceptor(){
        return new SessionCorrectInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionCorrectInterceptor())
                .addPathPatterns("/inform/**");
        super.addInterceptors(registry);
    }
}

