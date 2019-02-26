package com.bw.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CommonApplication {

    @Bean
    @LoadBalanced
    public RestTemplate getRest(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

}
