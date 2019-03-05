package com.bw.fit.system.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.controller.BaseController;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.common.service.CommonService;
import com.bw.fit.system.common.util.PubFun;
import com.bw.fit.system.dict.mapper.DictMapper;
import com.bw.fit.system.menu.service.MenuService;
import com.bw.fit.system.user.entity.TUser;
import com.bw.fit.system.user.mapper.UserMapper;
import com.bw.fit.system.user.model.User;
import com.bw.fit.system.user.service.UserService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.bw.fit.system.common.util.PubFun.getUUID;
import static com.bw.fit.system.common.util.PubFun.returnFailJson;

/****
 * 用户层，controller
 * @author yangh
 *
 */
@RequestMapping(value="user",produces = "application/json; charset=utf-8")
@Controller
@EnableEurekaClient
public class UserController  extends BaseController {

    @Resource
    private DictMapper dictMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CommonService commonService;

    @RequestMapping(value="users",method= RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject users(@ModelAttribute User user){
        JSONObject json = new JSONObject();
        Page<TUser> list = userService.all(user);
        for(TUser tu:list){
            tu.setType(dictMapper.getDictByValue(tu.getType()).getDictName());
        }
        json.put("total",list.getTotal());
        json.put("rows", JSONObject.toJSON(list));
        return json;
    }

    @GetMapping(value = "user/{id}/{sessionId}" )
    @ResponseBody
    public String openUserDetail(@PathVariable String id,@PathVariable String sessionId, Model model){
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        TUser tu = userMapper.get(id);
        PubFun.copyProperties(user, tu);
        user.setType(dictMapper.getDictByValue(user.getType()).getDictName());
        user.setGender(dictMapper.getDictByValue(user.getGender()).getDictName());
        user.setIsVisible(dictMapper.getDictByValue(user.getIsVisible()).getDictName());

        return ((JSONObject)JSONObject.toJSON(user)).toJSONString();
    }

    @RequestMapping(value = "user",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject insert(@Valid @ModelAttribute User user, Model model, BindingResult result){
        JSONObject json = new JSONObject();
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            json.put("res", "1");
            returnFailJson(json, error.getDefaultMessage());
            return json ;
        }
        try { 
            user.setId(getUUID());
            json = userService.add(user);
        } catch (RbackException e) {
            e.printStackTrace();
            json = new JSONObject();
            returnFailJson(json, e.getMsg());
        }finally{
            return json  ;
        }
    }


    @RequestMapping(value = "user/{id}",method=RequestMethod.DELETE,produces="application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject delete(@PathVariable String id){
        JSONObject json = new JSONObject();
        try {
            json = userService.delete(id);
        } catch (RbackException e) {
            e.printStackTrace();
            json = new JSONObject();
            returnFailJson(json, e.getMsg());
        }finally{
            return json  ;
        }
    }
}
