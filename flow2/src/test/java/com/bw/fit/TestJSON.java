package com.bw.fit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.util.MqFlowResultSender;
import com.bw.fit.component.form.conf.MqDyncFormSender;
import com.bw.fit.component.form.model.Form;
import com.bw.fit.component.form.util.MqDyncFormRecv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    MqDyncFormSender mqDyncFormSender;
    @Resource
    MqDyncFormRecv mqDyncFormRecv;

    @Test
    public void test1() throws Exception {
        Form form = new Form();
        Map<String,String> map = new HashMap<>();
        map.put("listtab;1;测试list","支付宝:天猫:dingd234, 支付宝:天猫:dingd23433, 支付宝:天猫:dingding2, 支付宝:;天猫:dingding3, 支付宝:天猫:dingding4");
        form.setListForm(map);
        map = new HashMap<>();
        map.put("kvtab;2;测试tab","姓名:张三,年龄:23,性别:男,毕业院校:清华大学计算机学3344");
        form.setKvForm(map);
        form.setId("003");
        form.setCreator("qq1");
        String s = JSONObject.toJSONString(form);
        mqDyncFormSender.sendDyncForm(s);

        //mqDyncFormRecv.recvDynvForm();
    }

}
