package com.bw.fit.component.form.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.service.CommonService;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.entity.TForm;
import com.bw.fit.component.form.mapper.FormMapper;
import com.bw.fit.component.form.service.FormPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:52
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service
public class FormPlusServiceImpl implements FormPlusService {
    @Resource
    private FormMapper formMapper;
    @Autowired
    private CommonService commonService;

    @Override
    public JSONObject insert(JSONArray form, String accountId) throws RbackException {
        JSONObject jsonObject = new JSONObject();
        try{
            String formKey = "";
            for(int i=0;i<form.size();i++){
                TForm tForm = JSONObject.toJavaObject(JSONObject.parseObject(form.get(i).toString()),TForm.class);
                tForm.setId(PubFun.getUUID());
                tForm.setCreator(accountId);
                formMapper.insert(tForm);
                formKey = tForm.getFormKey();
            }
            PubFun.returnSuccessJson(jsonObject);
            jsonObject.put("formKey",formKey);
        }catch (RbackException e){
            e.printStackTrace();
            jsonObject = new JSONObject();
            PubFun.returnFailJson(jsonObject,e.getMsg());
            throw  e;
        }finally {
            return jsonObject;
        }
    }
}
