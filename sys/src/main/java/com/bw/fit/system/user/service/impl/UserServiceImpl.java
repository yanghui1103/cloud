package com.bw.fit.system.user.service.impl;
import  static com.bw.fit.system.common.util.PubFun.*;
import    com.bw.fit.system.common.util.*;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.mapper.AccountMapper;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.common.model.RbackException;
import com.bw.fit.system.user.entity.TUser;
import com.bw.fit.system.user.mapper.UserMapper;
import com.bw.fit.system.user.model.User;
import com.bw.fit.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value="userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AccountMapper accountMapper;

    @Override
    public JSONObject add(User user) throws RbackException {
        JSONObject json = new JSONObject();
        try{
            TUser tuser = new TUser();
            PubFun.copyProperties(tuser, user);
            userMapper.insert(tuser);
            PubFun.returnSuccessJson(json);
        }catch(RbackException ex){
            json = new JSONObject();
            PubFun.returnFailJson(json, ex.getMsg());
        }finally{
            return json;
        }
    }

    @Override
    public JSONObject delete(String id) throws RbackException {
        JSONObject json = new JSONObject();
        try{
            userMapper.delete(id);
            PubFun.returnSuccessJson(json);
        }catch(RbackException ex){
            json = new JSONObject();
            PubFun.returnFailJson(json, ex.getMsg());
        }finally{
            return json;
        }
    }

    @Override
    public User get(String id) {
        User user = new User();
        TUser tu =  userMapper.get(id);
        if(tu!=null){
            PubFun.copyProperties(user, tu);
        }
        return user ;
    }

    @Override
    public User getByCode(String code) {
        User user = new User();
        TUser tu =  userMapper.getByCode(code);
        if(tu!=null){
            PubFun.copyProperties(user, tu);
        }
        return user ;
    }

    @Override
    public void createUserBySAP(TUser user, Account account, String accountId, String positionId, String orgId)
            throws RbackException {

        if(userMapper.get(user.getId())!=null) {
            if("C".equals(user.getTemp_str1())) {
                userMapper.delete(user.getId());
                accountMapper.delete(user.getId());
            }else if("A".equals(user.getTemp_str1())) {
                userMapper.update(user);
                accountMapper.update(account);
            }
        }else {
            if("A".equals(user.getTemp_str1())) {
                userMapper.insert(user);
                accountMapper.insert(account);
                accountMapper.insertAccount2Org(accountId, orgId);
                accountMapper.insertAccount2Position(accountId, positionId);
            }
        }

    }

}

