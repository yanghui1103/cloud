package com.bw.fit.base.inform.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.controller.BaseController;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.common.service.CommonService;
import com.bw.fit.base.common.util.PubFun;
import com.bw.fit.base.inform.entity.TInform;
import com.bw.fit.base.inform.mapper.InformMapper;
import com.bw.fit.base.inform.model.Inform;
import com.bw.fit.base.inform.service.InformService;
import com.github.pagehelper.Page;
import oracle.jdbc.proxy.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import static com.bw.fit.base.common.util.PubFun.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.Line;
import javax.validation.Valid;
import java.util.List;

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
    @Resource
    private InformMapper informMapper;

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

    /****
     * 获取当前账户的站内信
     * @param inform
     * @param httpServletRequest
     * @return
     */
    @GetMapping("innerMsg")
    public JSONObject innermsgs(@ModelAttribute Inform inform,HttpServletRequest httpServletRequest){
        JSONObject accountJson = commonService.getCurrentAccount(httpServletRequest);
        Inform inform1 = new Inform();
        inform.setReceiver(accountJson.getString("id"));
        JSONObject jsonObject = new JSONObject();
        List<Inform> list = informService.selectInnerInform(inform);
        jsonObject.put("total",((Page)list).getTotal());
        jsonObject.put("rows",(JSONArray) JSONObject.toJSON(list));
        return jsonObject;
    }

    @GetMapping("innerMsg/{id}")
    public String getOne(@PathVariable String id){
        JSONObject jsonObject = new JSONObject();
        TInform tInform = informMapper.get(id);
        if(ObjectUtil.isNotNull(tInform)){
            jsonObject = (JSONObject)JSONObject.toJSON(tInform);
            PubFun.returnSuccessJson(jsonObject);
        }
        PubFun.returnFailJson(jsonObject,"不存在数据");
        return jsonObject.toJSONString();
    }

    @PutMapping("innerMsg/{id}")
    public JSONObject yiyue(@PathVariable String id) throws RbackException {
        JSONObject jsonObject = informService.updateReadInnerMsg(id);
        return jsonObject;
    }

}
