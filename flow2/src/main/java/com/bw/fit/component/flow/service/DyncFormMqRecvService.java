package com.bw.fit.component.flow.service;

import com.alibaba.fastjson.JSONObject;

/*****
 * 接收动态表单数据
 */
public interface DyncFormMqRecvService {
    /*****
     * 接收动态表单数据接入到flow2工程里
     * @param content
     * @return
     */
    JSONObject processFormInFlowProj(String content);
}
