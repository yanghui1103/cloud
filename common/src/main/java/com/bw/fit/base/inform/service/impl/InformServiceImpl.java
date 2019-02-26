package com.bw.fit.base.inform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.util.PubFun;
import com.bw.fit.base.inform.entity.TInform;
import com.bw.fit.base.inform.model.Inform;
import com.bw.fit.base.inform.service.InformService;
import com.bw.fit.base.inform.util.InnerMsgSender;
import com.bw.fit.base.inform.util.MailTool;
import com.bw.fit.base.inform.util.SmsSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-25 17:14
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service
public class InformServiceImpl implements InformService {

    @Resource
    private InnerMsgSender innerMsgSender;

    @Override
    public JSONObject send(Inform inform) {
        JSONObject jsonObject = new JSONObject();
        switch (inform.getWay()){
            case "sms":
                PubFun.returnSuccessJson(jsonObject);
                return jsonObject;
            case "email":
                MailTool.send(null,null,null);
                PubFun.returnSuccessJson(jsonObject);
                return jsonObject;
            default:
                TInform tInform = new TInform();
                PubFun.copyProperties(tInform,inform);
                jsonObject = innerMsgSender.send(tInform);
                return jsonObject;
        }
    }
}