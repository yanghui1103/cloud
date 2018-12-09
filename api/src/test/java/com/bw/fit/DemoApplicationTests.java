package com.bw.fit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.pc.sys.util.PubFun;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void contextLoads() {
        System.out.print(PubFun.getUUID());
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;

    @Test
    public void tesf(){
        String key = "1002";
        ResponseEntity<String> js =
        restTemplate.exchange("http://localhost:9001/cache/delete", HttpMethod.DELETE,
                new HttpEntity<String>("1002"),
                String.class);

        System.out.println(js.toString());
    }

}
