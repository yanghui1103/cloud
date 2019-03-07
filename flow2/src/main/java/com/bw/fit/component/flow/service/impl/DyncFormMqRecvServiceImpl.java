package com.bw.fit.component.flow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.conf.RabbitMqConfig;
import com.bw.fit.component.flow.service.DyncFormMqRecvService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DyncFormMqRecvServiceImpl implements DyncFormMqRecvService {

    @Override
    public JSONObject processFormInFlowProj(String content) {
        System.out.println("form recv:"+content);
        return null;
    }
}
