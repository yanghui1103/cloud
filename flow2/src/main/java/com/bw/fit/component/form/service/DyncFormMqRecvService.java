package com.bw.fit.component.form.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-8 10:00
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface DyncFormMqRecvService {
    /*****
     * 接收动态表单数据接入到flow2工程里
     * @param content
     * @return
     */
    JSONObject processFormInFlowProj(String content)    throws Exception ;
}
