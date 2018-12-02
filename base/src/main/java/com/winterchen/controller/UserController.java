package com.winterchen.controller;

import com.alibaba.fastjson.JSONObject;
import com.winterchen.dao.UserDao;
import com.winterchen.model.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserDao userDao;

    @ResponseBody
    @GetMapping("/add")
    public int addUser(){
        System.out.println("test  .......");
        JSONObject json = new JSONObject();
        json.put("res","123");
        System.out.println(json.get("res"));

        //UserDomain user = new UserDomain();
        //userDao.insert(user);
        return 2;
    }

}
