package com.bw.fit.component.form.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.conf.RabbitMqConfig;
import com.bw.fit.component.form.service.DyncFormMqRecvService;
import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-8 10:00
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
public class DyncFormMqRecvServiceImpl implements DyncFormMqRecvService {

    @Override
    public JSONObject processFormInFlowProj(String message) throws Exception {


        return null;
    }
}
