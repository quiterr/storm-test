package com.quiterr.redis;

/**
 * Redis工具类
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by xuetao on 2017/5/2.
 *
 * redis 工具类
 *
 */
@SuppressWarnings("unchecked")
@Component
public class RedisService<K,V> {
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate<K,V> redisTemplate;
    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final K... keys) {
        for (K key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final K pattern) {
        Set<K> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final K key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final K key) {
        boolean result = false;
        try {
            result = redisTemplate.hasKey(key);
        }
        catch (NullPointerException e){
            return false;
        }
        System.out.print("----------"+result);
        return result;
    }
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public V get(final K key) {
        V result = null;
        ValueOperations<K, V> operations;
        try {
            operations = redisTemplate.opsForValue();
        }
        catch (NullPointerException e){
            return null;
        }

        try {
            result = operations.get(key);
        }
        catch (NullPointerException e){
            return null;
        }
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final K key, V value) {
        boolean result = false;
        try {
            ValueOperations<K, V> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final K key, V value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<K, V> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新生存时间
     */
    public boolean updateExprieTime(final K key,Long expireTime){
        boolean result = false;
        try{
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            System.out.println("last time :" + redisTemplate.getExpire(key));
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}