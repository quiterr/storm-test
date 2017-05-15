package com.quiterr.redis;

/**
 * Created by xuetao on 2017/5/4.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Redis sentinel properties.
 */
@Configuration
public class Sentinel {

    /**
     * Name of Redis server.
     */
    @Value("${spring.redis.sentinel.master:@null}")
    private String master;

    /**
     * Comma-separated list of host:port pairs.
     */
    @Value("${spring.redis.sentinel.nodes:@null}")
    private String nodes;

    public String getMaster() {
        return this.master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getNodes() {
        return this.nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

}