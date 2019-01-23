package com.bw.fit.system.account.service.impl;

import com.bw.fit.system.account.entity.TAccount;
import com.bw.fit.system.account.mapper.AccountMapper;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.model.BaseModel;
import com.bw.fit.system.common.util.PubFun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public Account get(String logName, BaseModel baseModel) {
        Account account = new Account();
        TAccount tAccount= accountMapper.getByLogName(logName);
        PubFun.copyProperties(account,tAccount);

        return account;
    }
}
