package com.bw.fit.base.log.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.controller.BaseController;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.common.service.CommonService;
import com.bw.fit.base.log.model.Log;
import com.bw.fit.base.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value="log",produces = "application/json; charset=utf-8")
@RestController
public class LogController extends BaseController {

    @Autowired
    private LogService logService;
    @Autowired
    private CommonService commonService;

    /*****
     * 记录日志接口
     * @param log
     * @param bindingResult
     * @return
     * @throws RbackException
     */
    @PostMapping("log")
    public String create(@Valid @ModelAttribute Log log, BindingResult bindingResult, HttpServletRequest httpServletRequest) throws RbackException {
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldError();
            jsonObject.put("res", "1");
            returnFailJson(jsonObject, error.getDefaultMessage());
            return jsonObject.toJSONString();
        }
        commonService.fillCommonProptities(log,httpServletRequest,true);
        jsonObject = logService.log(log);
        return  jsonObject.toJSONString();
    }

    /*****
     * 翻页获取日志列表
     * @param log
     * @return
     */
    @GetMapping("log")
    public String list(@ModelAttribute Log log){
        return  logService.all(log).toJSONString();
    }

    @GetMapping(value="log/{id}")
    public String get(@PathVariable String id){
        return logService.get(id).toJSONString();
    }

}
