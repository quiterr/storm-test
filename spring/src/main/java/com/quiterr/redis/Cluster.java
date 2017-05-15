package com.quiterr.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuetao on 2017/5/3.
 */
@Configuration
public class Cluster {

    /**
     * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
     * "initial" list of cluster nodes and is required to have at least one entry.
     */
    @Value("${spring.redis.cluster.nodes:@null}")
    private String nodes;

    /**
     * Maximum number of redirects to follow when executing commands across the
     * cluster.
     */
    @Value("${spring.redis.cluster.max-redirects:@null}")
    private Integer maxRedirects;


    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public List<String> getNodes() {
        if (nodes != null && nodes.trim().length() > 0) {
            String[] sNodes = nodes.split(",");
            if (sNodes != null && sNodes.length > 0) {
                return Arrays.asList(sNodes);
            }
        }
        return null;
    }

    public Integer getMaxRedirects() {
        return this.maxRedirects;
    }

    public void setMaxRedirects(Integer maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

}