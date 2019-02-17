package com.bw.fit;

import com.bw.fit.system.account.mapper.AccountMapper;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.account.service.AccountService;
import com.bw.fit.system.common.model.BaseModel;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysApplicationTest {

    @Value("${server.port}")
    String age;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AccountService accountService;

    @Test
    public void load(){
        BaseModel baseModel = new BaseModel();
        baseModel.setTenantId("tenantcm001");
        Account account = accountService.get("admin",baseModel);
        System.out.println(account);
    }
    @Test
    public void getbdc23() {

        String result = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Accept", "application/json");
        MultiValueMap<String, String> paramMapt = new LinkedMultiValueMap<String, String>() {
            {
                this.add("loginname", "loginname");
                this.add("password", "passpasss");
                this.add("sessionId", "123213123123123");
            }
        };

        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<MultiValueMap<String, String>>(paramMapt, headers);
        String url = "http://localhost:8001/account/menus/123";

//        ResponseEntity<String> result = restTemplate.postForEntity(url, formEntity, String.class,map);

//        System.out.println(result.getBody());

        result = restTemplate.getForObject(url, String.class, formEntity);
        System.out.println("formEntity:" + result);
    }

    @Test
    public void pp(){
        System.out.println("age:"+age);
    }

}
