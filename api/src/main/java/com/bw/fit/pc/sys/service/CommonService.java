package com.bw.fit.pc.sys.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @Author yangh
 * @Date 2018-12-8 12:43
 * @Param ${PARAM}
 * @Return ${RETURN}
 * @VERSION
 */
public interface CommonService {

    /**
     * 功能描述: 创建缓存数据
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-8 12:49
     */
    JSONObject setCacheValue(String key, JSONObject jsonObject);

    /**
     * 功能描述: 获取缓存库里的值
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-8 12:50
     */
    String getCacheValue(String key);

    /**
     * 功能描述: 删除缓存
     *
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-8 19:20
     */
    JSONObject deleteCache(String key);

    public JSONObject getOtherAppReturn(String url);

}
