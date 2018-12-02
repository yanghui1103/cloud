package com.bw.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowApplication.class, args);
    }
}
