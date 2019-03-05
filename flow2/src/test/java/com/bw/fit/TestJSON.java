package com.bw.fit;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @Description
 * @Author yangh
 * @Date 2019-3-5 8:43
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJSON {

    @Test
    public void test(){
        String ss =  "支付宝;天猫;dingding0, 支付宝;天猫;dingding1, 支付宝;天猫;dingding2, 支付宝;天猫;dingding3, 支付宝;天猫;dingding4";
        for(String s:ss.split(",")){
            Arrays.asList(s.split(";")).stream().forEach(x->{
                //System.out.println(x);
            });
        }

        String dd = "姓名:张三,年龄:23,性别:男";
        Arrays.asList(dd.split(",")).stream().forEach(x->{
            System.out.println(x);
        });

    }

}
