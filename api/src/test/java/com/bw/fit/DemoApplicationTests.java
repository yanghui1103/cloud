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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    private  static Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);
    @Autowired

    @Test
    public void contextLoads() {
        System.out.print(PubFun.getUUID());
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @Autowired
    private CommonService commonService;

    @Test
    public void tesf(){
        String key = "1001";
        restTemplate.delete("http://localhost:9001/cache/cache/cache/"+key);

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
}
