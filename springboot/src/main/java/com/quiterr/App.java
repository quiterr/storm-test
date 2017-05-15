package com.quiterr;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by Huangchen on 2017/5/12.
 */
public class App {
    public static void main(String args[]){
        Config conf = new Config();
        conf.setDebug(true);
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word", new TestWordSpout(), 1);
        builder.setBolt("exclaim", new ExclamationBolt(), 1).shuffleGrouping("word");
//        try {
//            LocalCluster cluster = new LocalCluster();
//            cluster.submitTopology("test", conf,builder.createTopology());
//            Thread.sleep(60 * 1000);
//            cluster.shutdown();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            StormSubmitter.submitTopology("test", conf,
                    builder.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
