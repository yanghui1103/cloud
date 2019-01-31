package com.bw.fit.system.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.system.account.model.Account;
import com.bw.fit.system.common.model.BaseModel;
import com.bw.fit.system.common.service.CommonService;
import com.bw.fit.system.common.util.PubFun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author yangh
 * @Date 2019-1-30 15:39
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service(value="commonService")
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
            Account account = JSONObject.toJavaObject(accountJson,Account.class);
            baseModel.setCreator(account.getId());
            baseModel.setCreatorName(account.getName());
            baseModel.setCreateOrgId(account.getCurrentOrgId());
            baseModel.setCreateTime(PubFun.getSysDate());
            baseModel.setHaveOrgListAuth(account.getHaveOrgListAuth());
        }
    }
}
