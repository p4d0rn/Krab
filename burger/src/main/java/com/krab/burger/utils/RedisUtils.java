package com.krab.burger.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Object hget(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void hdel(String key){
        redisTemplate.delete(key);
    }

    public void hset(String key, Object value, Integer time){
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }
}
