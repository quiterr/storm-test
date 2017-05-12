package com.quiterr;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.redis.bolt.RedisLookupBolt;
import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisClusterConfig;
import org.apache.storm.redis.common.mapper.RedisLookupMapper;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.topology.TopologyBuilder;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Huangchen on 2017/4/20.
 */
public class App {
    public static void main(String args[]){
        String host = "172.19.3.135,172.19.3.136,172.19.3.137";
//        String host = "172.19.3.135";
        int port = 6379;
//        JedisPoolConfig poolConfig = new JedisPoolConfig.Builder()
//                .setHost(host).setPort(port).build();
        Set<InetSocketAddress> inetSocketAddressSet = new HashSet<InetSocketAddress>();

        inetSocketAddressSet.add(new InetSocketAddress("172.19.3.135",port));
        inetSocketAddressSet.add(new InetSocketAddress("172.19.3.136",port));
        inetSocketAddressSet.add(new InetSocketAddress("172.19.3.137",port));
        JedisClusterConfig jedisClusterConfig = new JedisClusterConfig.Builder().setNodes(inetSocketAddressSet).build();

        RedisLookupMapper lookupMapper = new WordCountRedisLookupMapper();
        RedisLookupBolt lookupBolt = new RedisLookupBolt(jedisClusterConfig, lookupMapper);

        RedisStoreMapper storeMapper = new WordCountStoreMapper();
        RedisStoreBolt storeBolt = new RedisStoreBolt(jedisClusterConfig, storeMapper);

        Config conf = new Config();
        conf.setDebug(true);
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word", new TestWordSpout(), 1);
        builder.setBolt("lookupBolt",lookupBolt,1).shuffleGrouping("word");
        builder.setBolt("count",new WordCountBolt(),1).shuffleGrouping("lookupBolt");
        builder.setBolt("storeBolt",storeBolt,1).shuffleGrouping("count");
        try {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", conf,builder.createTopology());
            Thread.sleep(60 * 1000);
            cluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
