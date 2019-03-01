package com.bw.fit.base.inform.service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.inform.entity.TInform;
import com.bw.fit.base.inform.model.Inform;

import java.util.List;

/**
 * @Description 消息服务
 * @Author yangh
 * @Date 2019-2-25 17:13
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface InformService {

    /****
     * 发送
     * @param inform
     * @return
     */
    public JSONObject send(Inform inform);

    /*****
     * 查询站内信
     * @param inform
     * @return
     */
    public List<Inform> selectInnerInform(Inform inform);

    /****
     * 标记已阅
     * @param id
     * @return
     */
    JSONObject updateReadInnerMsg(String id) throws RbackException;

}
