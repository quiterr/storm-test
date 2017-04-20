package com.quiterr;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Huangchen on 2017/4/12.
 */
public class MyBolt extends BaseBasicBolt{

    public static Logger logger = LoggerFactory.getLogger(MyBolt.class);

    public void execute(Tuple input, BasicOutputCollector collector) {
        String deviceId = (String) input.getValue(0);
//        Data data = (Data)input.getValue(1);
        Thread current = Thread.currentThread();
        logger.info("Thread: {}, deviceId: {}", current.getId(), deviceId);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("word"));
    }
}
