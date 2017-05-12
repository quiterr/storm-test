package com.quiterr;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Huangchen on 2017/5/12.
 */
public class WordCountBolt extends BaseRichBolt {
    public static Logger logger = LoggerFactory.getLogger(WordCountBolt.class);
    OutputCollector _collector;

    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }

    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        String strCount = tuple.getString(1);
        Integer iCount = 0;
        if(strCount!=null){
            iCount = Integer.parseInt(strCount);
            iCount++;
        }
        logger.info("word: {},count: {}",word,iCount);
        _collector.emit(tuple, new Values(word,iCount.toString()));
        _collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word","count"));
    }
}
