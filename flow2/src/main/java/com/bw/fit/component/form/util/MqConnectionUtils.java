package com.bw.fit.component.form.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-8 11:05
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
public class MqConnectionUtils {
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
    /**
     * 获取MQ的连接
     * @return
     * @throws TimeoutException
     * @throws IOException
     */
    public Connection  getConnection() throws IOException, TimeoutException{
        //定义一个连接工厂
        ConnectionFactory factory =new ConnectionFactory();
        //设置服务地址
        factory.setHost(host);
        //AMQP 5672
        factory.setPort(port);
        //vhost
        factory.setVirtualHost(vhost);
        //用户名
        factory.setUsername(username);
        //密码
        factory.setPassword(password);
        return factory.newConnection();
    }

}
