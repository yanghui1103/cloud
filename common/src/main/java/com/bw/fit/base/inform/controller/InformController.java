package com.bw.fit.base.inform.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.controller.BaseController;
import com.bw.fit.base.common.service.CommonService;
import com.bw.fit.base.common.util.PubFun;
import com.bw.fit.base.inform.model.Inform;
import com.bw.fit.base.inform.service.InformService;
import oracle.jdbc.proxy.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.bw.fit.base.common.util.PubFun.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description 消息服务接口
 * @Author yangh
 * @Date 2019-2-26 16:39
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@EnableEurekaClient
@RequestMapping("inform")
@RestController
public class InformController extends BaseController {
    public  static Logger logger = LoggerFactory.getLogger(InformController.class);
    @Autowired
    private InformService informService;
    @Autowired
    private CommonService commonService;

    /******
     * 发送消息
     * @param inform
     * @param httpServletRequest
     * @return
     */
    @PostMapping("inform")
    public JSONObject create(@Valid @ModelAttribute Inform inform, BindingResult bindingResult, HttpServletRequest httpServletRequest){
        JSONObject jsonObject = new JSONObject();
        logger.info("insert...");
        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldError();
            jsonObject.put("res", "1");
            returnFailJson(jsonObject, error.getDefaultMessage());
            return jsonObject;
        }
        commonService.fillCommonProptities(inform,httpServletRequest,true);
        jsonObject = informService.send(inform);
        return  jsonObject;
    }

}
