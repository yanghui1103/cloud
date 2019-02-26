package com.bw.fit.base.common.service;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.entity.BaseEntity;
import com.bw.fit.base.common.model.BaseModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-26 17:02
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface CommonService {
    /**
     * 功能描述: 获取缓存库里的值
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-8 12:50
     */
    String getCacheValue(String key);

    /*****
     * 获取当前请求的账户信息，如果request的参数里没有sessionId，就不会取数据。
     * @param request
     * @return
     */
    JSONObject getCurrentAccount(HttpServletRequest request);

    /*****
     * 将basemodel的公共字段填充；
     * @param baseModel
     * @param request
     */
    void fillCommonProptities(BaseModel baseModel, HttpServletRequest request, boolean isFillFdid);
    /*****
     * 将baseEntity的公共字段填充；
     * @param baseEntity
     * @param request
     */
    void fillCommonProptities(BaseEntity baseEntity, HttpServletRequest request, boolean isFillFdid);

    /*****
     * 验证对应会话id是否有效;如果有效则会返回次会话的账户全量信息
     * @param sessionId
     * @return
     */
    public JSONObject checkSessionValid(String sessionId);
}
