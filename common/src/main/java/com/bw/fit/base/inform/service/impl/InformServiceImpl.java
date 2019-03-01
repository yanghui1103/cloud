package com.bw.fit.base.inform.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.common.util.PubFun;
import com.bw.fit.base.inform.entity.TInform;
import com.bw.fit.base.inform.mapper.InformMapper;
import com.bw.fit.base.inform.model.Inform;
import com.bw.fit.base.inform.service.InformService;
import com.bw.fit.base.inform.util.InnerMsgSender;
import com.bw.fit.base.inform.util.MailTool;
import com.bw.fit.base.inform.util.SmsSender;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private InformMapper informMapper;

    @Transactional(rollbackFor = {Exception.class, RbackException.class})
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

    @Override
    public List<Inform> selectInnerInform(Inform inform) {
        Page<Inform> ins = new Page<>();
        PageHelper.startPage(inform.getPage(),inform.getRows());
        List<TInform> tInforms = informMapper.getInnerMsgs(inform);
        if(CollectionUtil.isNotEmpty(tInforms)){
            tInforms.parallelStream().forEach(x->{
                Inform inform1 = new Inform();
                PubFun.copyProperties(inform1,x);
                ins.add(inform1);
            });
            ins.setTotal(((Page)tInforms).getTotal());
        }
        return ins;
    }

    @Transactional(rollbackFor = {Exception.class,RbackException.class})
    @Override
    public JSONObject updateReadInnerMsg(String id)  throws RbackException{
        JSONObject jsonObject = new JSONObject();
        try{
            informMapper.updateReadInnerMsg(id);
            PubFun.returnSuccessJson(jsonObject);
        }catch (Exception ex){
            PubFun.returnFailJson(jsonObject,"标记失败，发生异常");
            throw new RbackException("1","标记失败，发生异常");
        }finally {
            return jsonObject;
        }

    }
}
