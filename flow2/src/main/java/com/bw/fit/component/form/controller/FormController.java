package com.bw.fit.component.form.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.model.Form;
import com.bw.fit.component.form.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:36
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Controller
@RequestMapping(value="form")
@EnableEurekaClient
public class FormController {
    @Autowired
    private FormService formService;


    @PostMapping("form")
    @ResponseBody
    public String insert(@RequestParam(value="form" ,required = true) String form, BindingResult result, HttpServletRequest request) throws RbackException {
        JSONObject json = new JSONObject();
        JSONObject jsonObject = formService.insert(JSONArray.parseArray(form),request);
        return jsonObject.toJSONString();
    }

}
