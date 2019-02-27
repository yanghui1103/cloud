package com.bw.fit.base.log.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.common.service.CommonService;
import com.bw.fit.base.common.util.PubFun;
import com.bw.fit.base.common.util.RestTemplateUtil;
import com.bw.fit.base.inform.service.InformService;
import com.bw.fit.base.log.entity.TLog;
import com.bw.fit.base.log.mapper.LogMapper;
import com.bw.fit.base.log.model.Log;
import com.bw.fit.base.log.service.LogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-25 11:36
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private InformService informService;
    @Resource
    private LogMapper logMapper;
    @Resource
    private RestTemplateUtil restTemplateUtill;

    @Transactional(rollbackFor = {Exception.class,RbackException.class})
    @Override
    public JSONObject log(Log log)  throws RbackException {
        JSONObject jsonObject = new JSONObject();
        TLog tLog = new TLog();
        PubFun.copyProperties(tLog,log);
        logMapper.insert(tLog);
        if ("1".equals(log.getWantInform())){
            JSONObject j = informService.send(log.getInform());
            if("1".equals(j.get("res"))){
                throw new RbackException("1","消息发送失败");
            }
        }
        PubFun.returnSuccessJson(jsonObject);
        return jsonObject;
    }

    @Override
    public List<TLog> all(Log log) {
        PageHelper.startPage(log.getPage(),log.getRows());
        TLog tLog = new TLog();
        PubFun.copyProperties(tLog,log);
        List<TLog> tLogs = logMapper.selectAll(tLog);
        return tLogs;
    }

    @Override
    public JSONObject get(String id, HttpServletRequest httpServletRequest) {
        TLog tLog = logMapper.get(id);
        MultiValueMap<String, ?>  map = new LinkedMultiValueMap<>();
        String accountString =restTemplateUtill.get(httpServletRequest,"http://sys-proj/account/account/id/"+tLog.getCreator(),map);
        JSONObject jsonObject = JSONObject.parseObject(accountString);
        tLog.setCreator(jsonObject.getString("name"));
        return (JSONObject)JSONObject.toJSON(tLog);
    }
}
