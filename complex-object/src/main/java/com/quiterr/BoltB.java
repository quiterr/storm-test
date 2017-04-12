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
public class BoltB extends BaseBasicBolt{

    public static Logger logger = LoggerFactory.getLogger(BoltB.class);

    public void execute(Tuple input, BasicOutputCollector collector) {
        Data data = (Data) input.getValue(0);
        logger.info(data.getStr());
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
