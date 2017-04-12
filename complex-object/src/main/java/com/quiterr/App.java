package com.quiterr;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by Huangchen on 2017/4/12.
 */
public class App {
    public static void main(String args[]){
        Config conf = new Config();
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("word", new MySpout(), 1);
        builder.setBolt("bolt1", new BoltA(), 1).shuffleGrouping("word");
        builder.setBolt("bolt2", new BoltB(), 1).shuffleGrouping("word");

        conf.setDebug(true);

        String topologyName = "MyTopology";

        //集群运行
//        try {
//            StormSubmitter.submitTopology(topologyName, conf,
//                    builder.createTopology());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //本地运行
        try {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(topologyName, conf,builder.createTopology());
            Thread.sleep(60 * 1000);
            cluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
