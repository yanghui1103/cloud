package com.bw.fit.base.inform.util;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.common.util.PubFun;
import com.bw.fit.base.inform.entity.TInform;
import com.bw.fit.base.inform.mapper.InformMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 站内信工具类
 * @Author yangh
 * @Date 2019-2-25 17:05
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Component
public class InnerMsgSender {

    @Resource
    InformMapper informMapper;

    /*****
     * 发送站内信
     * @param tInform
     * @return
     */
    public JSONObject send(TInform tInform) {
        JSONObject jsonObject = new JSONObject();
        try {
            informMapper.insert(tInform);
            PubFun.returnSuccessJson(jsonObject);
        } catch (RbackException e) {
            e.printStackTrace();
            jsonObject = new JSONObject();
            PubFun.returnFailJson(jsonObject,e.getMsg());
        }finally {
            return jsonObject;
        }
    }



    /*****
     * 获取单挑记录的详情
     * @param id
     * @return
     */
    public TInform get(String id) {
        return null;
    }

    /*****
     * 获取某个全部站内信
     * @param accountId
     * @return
     */
    public List<TInform> select(String accountId) {
        return null;
    }
}
