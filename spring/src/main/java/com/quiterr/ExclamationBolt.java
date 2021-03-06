package com.quiterr;

import com.quiterr.redis.RedisService;
import com.quiterr.spring.StormContext;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

/**
 * Created by Huangchen on 2017/5/10.
 */
public class ExclamationBolt extends BaseRichBolt {

    public static Logger logger = LoggerFactory.getLogger(ExclamationBolt.class);

    RedisService<String,String> redisService;

    OutputCollector _collector;

    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        StormContext stormContext = StormContext.getStormContext();
        redisService = stormContext.getBean(RedisService.class);
    }

    public void execute(Tuple tuple) {
        redisService.set("tuple", tuple.getString(0));
        logger.info(redisService.get("tuple"));
        _collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

}
