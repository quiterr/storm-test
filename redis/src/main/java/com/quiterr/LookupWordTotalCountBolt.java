package com.quiterr;

import org.apache.storm.redis.bolt.AbstractRedisBolt;
import org.apache.storm.redis.common.config.JedisClusterConfig;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCommands;

import java.util.Random;

/**
 * Created by Huangchen on 2017/4/20.
 */
public class LookupWordTotalCountBolt extends AbstractRedisBolt {
    private static final Logger LOG = LoggerFactory.getLogger(LookupWordTotalCountBolt.class);
    private static final Random RANDOM = new Random();

    public LookupWordTotalCountBolt(JedisPoolConfig config) {
        super(config);
    }

    public LookupWordTotalCountBolt(JedisClusterConfig config) {
        super(config);
    }

    public void execute(Tuple input) {
        JedisCommands jedisCommands = null;
        try {
            jedisCommands = getInstance();
            String wordName = input.getStringByField("word");
            String countStr = jedisCommands.get(wordName);
            if (countStr != null) {
                int count = Integer.parseInt(countStr);
                this.collector.emit(new Values(wordName, count));

                // print lookup result with low probability
                if(RANDOM.nextInt(1000) > 995) {
                    LOG.info("Lookup result - word : " + wordName + " / count : " + count);
                }
            } else {
                // skip
                LOG.warn("Word not found in Redis - word : " + wordName);
            }
        } finally {
            if (jedisCommands != null) {
                returnInstance(jedisCommands);
            }
            this.collector.ack(input);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // wordName, count
        declarer.declare(new Fields("wordName", "count"));
    }
}
