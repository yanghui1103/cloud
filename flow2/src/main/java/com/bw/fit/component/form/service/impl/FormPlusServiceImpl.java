package com.bw.fit.component.form.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.model.RbackException;
import com.bw.fit.component.flow.service.CommonService;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.entity.TForm;
import com.bw.fit.component.form.mapper.FormMapper;
import com.bw.fit.component.form.model.Form;
import com.bw.fit.component.form.service.FormPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 16:52
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service
public class FormPlusServiceImpl implements FormPlusService {
    @Resource
    private FormMapper formMapper;
    @Autowired
    private CommonService commonService;

    @Override
    public JSONObject insert(Form form) throws RbackException {
        JSONObject jsonObject = new JSONObject();
        Map<String, String> map1 = form.getKvForm();
        if(CollectionUtil.isNotEmpty(map1)){
            for(String key:map1.keySet()){
                 String value = map1.get(key).toString();//
                TForm tForm = new TForm();
                tForm.setCreator(form.getCreator());
                tForm.setId(PubFun.getUUID());
                tForm.setAttr((value));
                tForm.setTabType(key.split(":")[0]);
                tForm.setTabOrder(Integer.valueOf(key.split(":")[1]));
                tForm.setFormKey(form.getId());
                tForm.setTabName(key.split(":")[2]);

                 System.out.println("key:"+key+" vlaue:"+value);
                formMapper.insert(tForm);
            }
        }

        Map<String,String> map2 = form.getListForm();
        if(CollectionUtil.isNotEmpty(map2)){
            for(String key:map2.keySet()){
                String value = map2.get(key).toString();//
                TForm tForm = new TForm();
                tForm.setCreator(form.getCreator());
                tForm.setId(PubFun.getUUID());
                tForm.setAttr((value));
                tForm.setTabType(key.split(":")[0]);
                tForm.setTabOrder(Integer.valueOf(key.split(":")[1]));
                tForm.setFormKey(form.getId());
                tForm.setTabName(key.split(":")[2]);

                System.out.println("key:"+key+" vlaue:"+value);
                formMapper.insert(tForm);
            }
        }


        Map<String,String> map3 = form.getAttachmentForm();
        if(CollectionUtil.isNotEmpty(map3)){
            for(String key:map3.keySet()){
                String value = map3.get(key).toString();
                TForm tForm = new TForm();
                tForm.setCreator(form.getCreator());
                tForm.setId(PubFun.getUUID());
                tForm.setAttr((value));
                tForm.setTabType(key.split(":")[0]);
                tForm.setTabOrder(Integer.valueOf(key.split(":")[1]));
                tForm.setFormKey(form.getId());
                tForm.setTabName(key.split(":")[2]);

                System.out.println("key:"+key+" vlaue:"+value);
                formMapper.insert(tForm);


            }
        }



        PubFun.returnSuccessJson(jsonObject);
        return jsonObject;
    }
}
