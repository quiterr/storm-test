package com.quiterr;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;


/**
 * Created by Huangchen on 2017/4/12.
 */
public class BoltA extends BaseBasicBolt{

    public void execute(Tuple input, BasicOutputCollector collector) {
        Data data = (Data)input.getValue(0);
        if(data.getStr().compareTo("a")==0){
            data.setStr("1");
        }else{
            data.setStr("2");
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
