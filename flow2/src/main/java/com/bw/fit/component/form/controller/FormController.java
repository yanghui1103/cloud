package com.bw.fit.component.form.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.service.CommonService;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.entity.TForm;
import com.bw.fit.component.form.mapper.FormMapper;
import com.bw.fit.component.form.model.KvForm;
import com.bw.fit.component.form.service.FormPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:36
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Controller
@RequestMapping(value="form",produces = "application/json; charset=utf-8")
@EnableEurekaClient
public class FormController {
    @Autowired
    private FormPlusService formPlusService;
    @Autowired
    private CommonService commonService;
    @Resource
    private FormMapper formMapper;



    @GetMapping("form/{formKey}")
    @ResponseBody
    public JSONObject detail(@PathVariable(value = "formKey",required = true) String formKey){
        JSONObject jsonObject = new JSONObject();
        List<TForm> tFormList = formMapper.getFormInfo(formKey);
        List<KvForm> kvForms = new CopyOnWriteArrayList<>();
        Map<String,String> map = new LinkedHashMap<>();
        JSONArray jsonArray = new JSONArray();
        if(CollectionUtil.isNotEmpty(tFormList) || tFormList.size()>1){
            tFormList.stream().forEach(x->{
                KvForm kvForm = new KvForm();
                PubFun.copyProperties(kvForm,x);
                kvForms.add(kvForm);
            });

            PubFun.returnSuccessJson(jsonObject);
            JSONArray array = (JSONArray)JSONArray.toJSON(kvForms);
            jsonObject.put("data",array);
        }else{
            PubFun.returnFailJson(jsonObject,"无数据");
        }
        return jsonObject;
    }
}
