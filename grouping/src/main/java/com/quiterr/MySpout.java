package com.quiterr;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * Created by Huangchen on 2017/4/12.
 */
public class MySpout extends BaseRichSpout {

    SpoutOutputCollector _collector;

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        _collector = collector;
    }

    public void close() {

    }

    public void nextTuple() {
        Utils.sleep(100);
        for(int i=1; i<=5; i++){
            Data data = new Data();
            data.setDeviceId(String.valueOf(i));
            data.setValue("anything");
            _collector.emit(new Values(data.getDeviceId(),data));
        }
    }

    public void ack(Object msgId) {

    }

    public void fail(Object msgId) {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("deviceId","data"));
    }

}
