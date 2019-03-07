package com.bw.fit.component.flow.conf;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ConnectionFactory 配置方法二
 * Created by yangliu on 2018/4/13.
 */
@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.vhost}")
    private String vhost;

    public static final String dyncFormQueue = "notify.createForm";
    public static final String cloudCommonExchange = "cloud_common_exchange";
    public static final String formRoutingKey = "cloud_flow2_createForm";


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host+":"+port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setPublisherConfirms(true);//消息确认
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }
}
