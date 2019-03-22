package com.bw.fit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.controller.LoginController;
import com.bw.fit.pc.sys.service.CommonService;
import com.bw.fit.pc.sys.util.PubFun;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    private MockHttpServletRequest request;
    private  static Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Test
    public void contextLoads() {
        System.out.print(PubFun.getUUID());
    }

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Environment env;
    @Autowired
    CommonService commonService;

    @Test
    public void tesf(){
        String key = "1001";
        restTemplate.delete("http://cacheApp/cache/cache/"+key);
        // System.out.println(js.toString());
    }

    @Test
    public void testRd(){
        JSONObject json = new JSONObject();
        json.put("name","sdfdsfd");
        json.put("gender","1");
        JSONObject j = commonService.setCacheValue("1003",json);
        logger.info(j.get("res").toString());
    }
    @Test
    public void test(){
        JSONObject jsonObject = restTemplate.getForObject("http://sys-proj/account/account/admin", JSONObject.class);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void startflow(){
        JSONObject jsonObject = restTemplate.getForObject("http://flow2-proj/flow/start/processDefinitionKey/", JSONObject.class);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void  testRest(){

        //创建request对象并设置字符编码
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("name", "123123123");
        params.add("desp", "qweqweqweqwe");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://sys-proj/role/role",
                HttpMethod.DELETE, requestEntity ,String.class) ;

        String s = response.getBody();
        System.out.println(s);
    }
}
