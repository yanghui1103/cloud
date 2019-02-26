package com.bw.fit.base.log.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bw.fit.base.common.entity.RbackException;
import com.bw.fit.base.log.model.Log;

/**
 * @Description
 * @Author yangh
 * @Date 2019-2-25 11:36
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface LogService {
    /****
     * 记录日志，并且如果有需要可以发起相应信息提醒
     * @param log
     * @return
     */
    JSONObject log(Log log) throws RbackException;

    /*****
     * 所有的日志记录
     * @return
     */
    JSONArray all(Log log);
}
