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
import com.bw.fit.component.form.service.FormPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private FormPlusService formPlusService;
    @Autowired
    private CommonService commonService;
    @Resource
    private FormMapper formMapper;


    /*****
     * 单笔将一个表单JSONArray传入进来，如果成功则会返回formKey
     * @param form
     * @param request
     * @return
     * @throws RbackException
     */
    @PostMapping("form")
    @ResponseBody
    public String insert(@RequestParam(value="form" ,required = true) String form, HttpServletRequest request) throws RbackException {
        JSONObject json = new JSONObject();
        try{

            json = commonService.checkSessionValid(request.getParameter("sessionId").toLowerCase());
            if("1".equalsIgnoreCase(json.get("res").toString())){
                json = new JSONObject();
                PubFun.returnFailJson(json,"无效会话");
                return json.toJSONString();
            }
            JSONObject jsonObject = formPlusService.insert(JSONArray.parseArray(form),json.get("id").toString());
            return jsonObject.toJSONString();
        }catch (RbackException ex){
            ex.printStackTrace();
            json = new JSONObject();
            PubFun.returnFailJson(json,ex.getMsg());
            return json.toJSONString();
        }
    }


    @GetMapping("form/{formKey}")
    @ResponseBody
    public String detail(@PathVariable(value = "formKey",required = true) String formKey){
        JSONObject jsonObject = new JSONObject();
        List<TForm> tFormList = formMapper.getFormInfo(formKey);
        Map<String,String> map = new LinkedHashMap<>();
        if(ObjectUtil.isNotNull(tFormList) && tFormList.size()>1){
            jsonObject.put("res","2");
            jsonObject.put("data",JSONArray.toJSON(tFormList));
        }else{
            jsonObject.put("res","1");
            jsonObject.put("msg","无表单数据");
        }
        return jsonObject.toJSONString();
    }
}
