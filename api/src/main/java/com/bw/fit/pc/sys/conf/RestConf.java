package com.bw.fit.pc.sys.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConf {
    @Bean
    public RestTemplate getRestTemplete(){
        return new RestTemplate();
    }
}
