package com.quiterr.redis;

/**
 * Created by xuetao on 2017/5/3.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Pool properties.
 */
@Configuration
public class Pool {

    /**
     * Max number of "idle" connections in the pool. Use a negative value to indicate
     * an unlimited number of idle connections.
     */
    @Value("${spring.redis.pool.max-idle:8}")
    private int maxIdle = 8;

    /**
     * Target for the minimum number of idle connections to maintain in the pool. This
     * setting only has an effect if it is positive.
     */
    @Value("${spring.redis.pool.min-idle:0}")
    private int minIdle = 0;

    /**
     * Max number of connections that can be allocated by the pool at a given time.
     * Use a negative value for no limit.
     */
    @Value("${spring.redis.pool.max-active:8}")
    private int maxActive = 8;

    /**
     * Maximum amount of time (in milliseconds) a connection allocation should block
     * before throwing an exception when the pool is exhausted. Use a negative value
     * to block indefinitely.
     */
    @Value("${spring.redis.pool.max-wait:-1}")
    private int maxWait = -1;

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return this.maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return this.maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

}