package com.quiterr;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Huangchen on 2017/5/10.
 */
@RestController
public class SubmitController {
    @RequestMapping(value = "/topology/create", method = RequestMethod.POST)
    private boolean create(@RequestParam("topologyName") String topologyName){
        Config conf = new Config();
        conf.setDebug(true);
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word", new TestWordSpout(), 10);
        builder.setBolt("exclaim1", new ExclamationBolt(), 3).shuffleGrouping("word");
        builder.setBolt("exclaim2", new ExclamationBolt(), 2).shuffleGrouping("exclaim1");

        System.setProperty("storm.jar", "/home/app/test/dynamic-submit-classes-1.0-SNAPSHOT.jar");
        try {
            StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        return true;
    }
}
