package com.bw.fit.base.log.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.controller.BaseController;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.log.model.Log;
import com.bw.fit.base.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.bw.fit.base.common.util.PubFun.returnFailJson;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-26 17:14
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@EnableEurekaClient
@RequestMapping("log")
@RestController
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    /*****
     * 记录日志接口
     * @param log
     * @param bindingResult
     * @return
     * @throws RbackException
     */
    @PostMapping("log")
    public JSONObject create(@Valid @ModelAttribute Log log, BindingResult bindingResult) throws RbackException {
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldError();
            jsonObject.put("res", "1");
            returnFailJson(jsonObject, error.getDefaultMessage());
            return jsonObject;
        }
        jsonObject = logService.log(log);
        return  jsonObject;
    }

}
