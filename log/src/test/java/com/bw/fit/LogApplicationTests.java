package com.bw.fit;

import com.bw.fit.log.common.util.RestTempleteUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogApplicationTests {
    //声明request变量
    private MockHttpServletRequest request;

    @Resource
    private RestTempleteUtil restTempleteUtil;
    @Resource
    RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        Map<String, String> params = new HashMap<>();
        params.put("name","yanghui");
        String string = restTempleteUtil.get(request,"http://report-proj/rpt/test?name={name}",params);
        System.out.println("str:" +string);

        Map<String,String> info=new HashMap<String,String>();
        info.put("name","DFCFMonitor");

        String result=restTemplate.getForObject("http://report-proj/rpt/test?name=005", String.class,info);
        System.out.println(result);
    }

}
