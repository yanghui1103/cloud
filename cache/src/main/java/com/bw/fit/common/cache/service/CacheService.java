package com.bw.fit.common.cache.service;

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
}
