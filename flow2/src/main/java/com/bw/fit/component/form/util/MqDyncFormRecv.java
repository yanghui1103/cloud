package com.bw.fit.component.form.util;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.conf.RabbitMqConfig;
import com.bw.fit.component.flow.util.PubFun;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.*;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description 接收动态表单数据
 * @Author yangh
 * @Date 2019-3-8 11:22
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
public class MqDyncFormRecv {
    @Resource
    MqConnectionUtils connectionUtils;

    @PostConstruct
    public JSONObject recvDynvForm()  {
        JSONObject jsonObject = new JSONObject();
        try{
            Connection connection = connectionUtils.getConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(RabbitMqConfig.dyncFormQueue, true, false, false, null);
            channel.queueBind(RabbitMqConfig.dyncFormQueue, RabbitMqConfig.cloudCommonExchange, RabbitMqConfig.formRoutingKey);

            channel.basicQos(1);
            Consumer consumer=new DefaultConsumer(channel){
                //消息到达 触发这个方法
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           BasicProperties properties, byte[] body) throws IOException {

                    String msg=new String(body,"utf-8");
                    System.out.println("[2] Recv msg:"+msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally{
                        System.out.println("[2] done ");
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };

            boolean autoAck=false;//自动应答 false
            channel.basicConsume(RabbitMqConfig.dyncFormQueue,autoAck , consumer);
            PubFun.returnSuccessJson(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            PubFun.returnFailJson(jsonObject,e.getLocalizedMessage());
        }

        return jsonObject ;
    }

}
