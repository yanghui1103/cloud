package com.bw.fit.component.form.util;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.conf.RabbitMqConfig;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.service.FlowPlusService;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.conf.MqTransferConf;
import com.bw.fit.component.form.model.Form;
import com.bw.fit.component.form.service.FormPlusService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.UnsupportedEncodingException;

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
    private static final Logger log = LoggerFactory.getLogger(MqDyncFormRecv.class);
    @Autowired
    private FormPlusService formPlusService;
    @Resource
    MqConnectionUtils connectionUtils;

    public void doCusmue(Channel channel,Envelope envelope,byte[] body) throws IOException {
        String recvedMsg =new String(body,"utf-8");
        System.out.println("Recv msg:"+recvedMsg);
        try {
            JSONObject object = JSONObject.parseObject(recvedMsg);
            Form form = JSONObject.toJavaObject(object,Form.class);
            JSONObject j =formPlusService.insert(form);
            if("2".equals(j.getString("res"))){
                channel.basicAck(envelope.getDeliveryTag(), false);
            }else{
                log.info("动态表单id"+form.getId()+":导入失败");
                channel.basicReject(envelope.getDeliveryTag(), true) ;  // 重新放回队列，true没做删除,false删除
            }
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicReject(envelope.getDeliveryTag(), true) ;  // 重新放回队列，true没做删除,false删除
        }
    }

    @PostConstruct
    public void recvDynvForm()  {
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
                    doCusmue(channel,envelope,body);
                    // requeue：重新入队列，true: 重新放入队列,false 则是让队列直接删除
                    //channel.basicReject(envelope.getDeliveryTag(), true);
                }
            };
            boolean autoAck = false;//自动应答 false
            channel.basicConsume(RabbitMqConfig.dyncFormQueue,autoAck , consumer);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
