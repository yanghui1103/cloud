package com.bw.fit.common.cache.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

public interface CacheService {
    /*****
     * 获取值
     * @param key
     * @return
     */
    Object get(String key);

    /****
     * 赋值
     * @param key
     * @param object
     */
    void set(String key,Object object);

    /****
     * 删除缓存key
     * @param key
     */
    void delete(String key);
    /**
     * 功能描述: 设置key在缓存库的里有效期；单位：秒
     * @param:
     * @return:
     * @auther: yangh
     * @date: 2018-12-9 19:25
     */
    boolean expire(String key,int seconds);
}
