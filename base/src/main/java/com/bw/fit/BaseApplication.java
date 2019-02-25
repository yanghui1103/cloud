package com.bw.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 基础组件工程
 * @Author yangh
 * @Date 2019-2-25 16:55
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableEurekaClient
public class BaseApplication {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

}
