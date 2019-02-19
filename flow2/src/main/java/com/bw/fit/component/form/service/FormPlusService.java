package com.bw.fit.component.form.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.model.RbackException;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:51
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface FormPlusService {
    /****
     *  增加一条表单记录数据
     * @return
     * @throws RbackException
     */
    JSONObject insert(JSONArray form, String accountId) throws RbackException;
}