package com.quiterr;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by Huangchen on 2017/4/18.
 */
public class App {
    public static void main(String args[]){
        Config conf = new Config();
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("source", new MySpout(), 1);
        builder.setBolt("bolt", new MyBolt(), 10).fieldsGrouping("source",new Fields("deviceId"));

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
