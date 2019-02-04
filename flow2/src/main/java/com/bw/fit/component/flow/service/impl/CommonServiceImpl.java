package com.bw.fit.component.flow.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.component.flow.service.CommonService;
import com.bw.fit.component.flow.util.PubFun;
import com.bw.fit.component.form.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-4 17:06
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public String getCacheValue(String key) {
        String response =
                restTemplate.getForObject("http://cache-proj/cache/key/" + key,  String.class);
        return response;
    }

    @Override
    public JSONObject getCurrentAccount(HttpServletRequest request) {
        JSONObject accountJSON = new JSONObject();
        String sessionId = request.getParameter("sessionId");
        if(StrUtil.isNotEmpty(sessionId)){
            String s = getCacheValue(sessionId);
            if(StrUtil.isNotEmpty(s)){
                return JSONObject.parseObject(s);
            }else{
                accountJSON.put("res","1");
                accountJSON.put("msg","会话ID无效或不存在");
                return accountJSON;
            }
        }else{
            accountJSON.put("res","1");
            accountJSON.put("msg","会话ID为空");
            return accountJSON;
        }
    }

    @Override
    public void fillCommonProptities(BaseModel baseModel, HttpServletRequest request,boolean isFillFdid) {
        JSONObject accountJson = getCurrentAccount(request);
        if("2".equals(accountJson.get("res"))){
            if(isFillFdid){
                baseModel.setId(PubFun.getUUID());
            }
            baseModel.setCreator(accountJson.get("id").toString());
            baseModel.setCreatorName(accountJson.get("name").toString());
            baseModel.setCreateOrgId(accountJson.get("currentOrgId").toString() );
            baseModel.setCreateTime(PubFun.getSysDate());
            baseModel.setHaveOrgListAuth(Arrays.asList(accountJson.get("haveOrgListAuth").toString()));
        }
    }

    @Override
    public JSONObject checkSessionValid(String sessionId) {
        String accountStr = getCacheValue("session:"+sessionId);
        if(StrUtil.isNotEmpty(accountStr)){
            return JSONObject.parseObject(accountStr);
        }
        JSONObject jsonObject = new JSONObject();
        PubFun.returnFailJson(jsonObject,"无效会话");
        return jsonObject ;
    }
}
