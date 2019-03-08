package com.bw.fit;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-8 8:25
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DyncForm {


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
    @Test
    public void testConn() throws IOException, TimeoutException {
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
        Connection connection = factory.newConnection();
        System.out.println(connection.getPort());

//        Channel channel = connection.createChannel();
//        //exchange
//        channel.exchangeDeclare("cloud_common_exchange", "direct");
//
//        String  msg="hello direct!";
//
//
//        String routingKey="cloud_flow2_createForm";
//        channel.basicPublish("cloud_common_exchange", routingKey, null, msg.getBytes());
//
//        System.out.println("send "+msg);
//        channel.close();
//        connection.close();

    }

    @Test
    public void testxiaofei() throws IOException, TimeoutException {
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
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        String QUEUE_NAME ="QUEUE_NAME1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        channel.queueBind(QUEUE_NAME, "cloud_common_exchange2", "cloud_flow2_createForm2");

        channel.basicQos(1);

        //定义一个消费者
        Consumer consumer=new DefaultConsumer(channel){
            //消息到达 触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       BasicProperties properties, byte[] body) throws IOException {

                String msg=new String(body,"utf-8");
                System.out.println("[2] Recv msg:"+msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    System.out.println("[2] done ");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        boolean autoAck=false;//自动应答 false
        channel.basicConsume(QUEUE_NAME,autoAck , consumer);
    }


    @Test
    public void tte(){
       // amqpTemplate.convertAndSend("cloud_common_exchange", "cloud_flow2_createForm", "im sender test ...");
    }


}
