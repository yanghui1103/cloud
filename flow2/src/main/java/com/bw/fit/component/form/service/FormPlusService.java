package com.bw.fit.component.form.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.form.entity.TForm;
import com.bw.fit.component.form.model.Form;

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
     * @param form 表单现在支持list，kv，附件的；但是key必须是tab类型:num:tab的名称
     * @return
     * @throws RbackException
     */
    JSONObject insert(Form form) throws Exception;
}
