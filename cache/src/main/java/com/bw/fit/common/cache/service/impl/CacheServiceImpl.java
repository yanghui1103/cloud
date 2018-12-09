package com.bw.fit.common.cache.service.impl;

import com.bw.fit.common.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, Object object) {
        redisTemplate.opsForValue().set(key,object);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
