package com.bw.fit.component.form.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-5 18:07
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Api("流程表单-API接口")
@Controller
@EnableEurekaClient
public class FormController {
    @Autowired
    private CommonService commonService;

    @ApiOperation("打开表单")
    @GetMapping(value="openFormDetail/{formKey}",produces = "application/json; charset=utf-8")
    public String formDetail(@PathVariable String formKey, Model model){
        Session session = PubFun.getCurrentSession();

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("sessionId", PubFun.getCurrentSessionId());
        String form = commonService.getOtherAppReturnString("http://flow2-proj/form/form/"+formKey, map);
        JSONObject jsonObject = JSONObject.parseObject(form);
        if("2".equals(jsonObject.get("res").toString())){
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            /****
             * 生成数据并最终有html渲染
             */
            model.addAttribute("data",jsonArray);
            model.addAttribute("formKey",formKey);
        }else{
            model.addAttribute("msg","抱歉，没有找到表单数据");
        }
        return "flow2/pc/component/form/formDetail";
    }

}
