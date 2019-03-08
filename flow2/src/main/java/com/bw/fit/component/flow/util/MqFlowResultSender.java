package com.bw.fit.component.flow.util;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.conf.RabbitMqConfig;
import com.bw.fit.component.form.util.MqConnectionUtils;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;


import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;


import javax.annotation.Resource;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-8 11:08
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
public class MqFlowResultSender {
    @Resource
    MqConnectionUtils connectionUtils;


    public JSONObject sendResult(String message) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Connection connection = connectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(RabbitMqConfig.cloudCommonExchange, "direct");
        //生产者调用confirmSelect 将channel设置为confirm模式 注意
        channel.confirmSelect();
        channel.basicPublish(RabbitMqConfig.cloudCommonExchange, RabbitMqConfig.formRoutingKey, null, message.getBytes());

        if (!channel.waitForConfirms()) {
            System.out.println("message send failed");
            PubFun.returnFailJson(jsonObject,"消息发送失败");
        } else {
            System.out.println("message send ok");
            PubFun.returnSuccessJson(jsonObject);
        }

        channel.close();
        connection.close();

        return jsonObject;
    }

}
