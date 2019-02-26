package com.bw.fit.component.form.conf;


import com.bw.fit.component.form.interceptor.SessionCorrectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/**
 * @Description
 * @Author yangh
 * @Date 2019-2-5 8:26
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Configuration
public class InterceptorConf extends WebMvcConfigurationSupport {
    @Bean
    SessionCorrectInterceptor getSessionCorrectInterceptor(){
        return new SessionCorrectInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionCorrectInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
