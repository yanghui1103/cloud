package com.bw.fit;

import com.bw.fit.system.account.mapper.AccountMapper;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.model.BaseModel;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysApplicationTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AccountService accountService;

    @Test
    public void load(){
        BaseModel baseModel = new BaseModel();
        baseModel.setTenant("tenantcm001");
        Account account = accountService.get("admin",baseModel);
        System.out.println(account);
    }


}
