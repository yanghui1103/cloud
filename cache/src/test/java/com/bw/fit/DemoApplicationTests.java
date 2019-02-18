package com.bw.fit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Value("${logSystemTempName}")
    String ssex;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void contextLoads() {
//        redisTemplate.opsForValue().set("key","qweqwe");
        System.out.println(ssex);
    }

}
