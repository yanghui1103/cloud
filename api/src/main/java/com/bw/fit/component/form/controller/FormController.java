package com.bw.fit.component.form.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
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
@Controller
@EnableEurekaClient
public class FormController {
    @Autowired
    private CommonService commonService;

    @GetMapping("openFormDetail/{formKey}")
    public String formDetail(@PathVariable String formKey, Model model){
        Session session = PubFun.getCurrentSession();
        Map<String,Object> map = new HashMap<>();
        map.put("sessionId",session.getId());
        String form = commonService.getOtherAppReturnString("http://flow2-proj/form/form/"+formKey, map);
        JSONObject jsonObject = JSONObject.parseObject(form);
        if("2".equals(jsonObject.get("res").toString())){
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            /****
             * 生成数据并最终有html渲染
             */
            model.addAttribute("data",jsonArray);
        }else{
            model.addAttribute("msg","抱歉，没有找到数据");
        }
        return "flow2/pc/component/form/formDetail";
    }
}
