package com.bw.fit;

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

    @Test
    public void load(){
        System.out.println(dataSource);
    }


}
