package com.bw.fit.component.form.conf;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.conf.RabbitMqConfig;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.util.MqConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-8 17:31
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
public class MqDyncFormSender {
    @Resource
    MqConnectionUtils connectionUtils;


    /****
     * 发送动态表单数据到Flow2工程
     * @param message
     * @return
     * @throws Exception
     */
    public JSONObject sendDyncForm(String message) throws Exception {
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
