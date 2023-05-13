package com.laowang.mymall.service.impl;

import com.laowang.mymall.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: my-mall
 * @description: redis操作Service的实现类
 * @author: Laowang
 * @create: 2023-05-13 17:04
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /** 搞笑的方法，不是检查key过没过期，仅仅只是设置过期时间而已！！
     * @param key key
     * @param expire 过期时间，代码里写死就是秒
     * @return 毫无意义
     */
    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key,expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    /** 搞笑方法，仅仅是设置自增，不要想成操作
     * @param key key
     * @param delta 自增步长
     * @return 不知道
     */
    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }
}
