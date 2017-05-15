package com.quiterr;

import com.alibaba.fastjson.JSON;
import com.chinamobile.iot.lightapp.mongo.client.model.DeviceData;
import com.chinamobile.iot.lightapp.mongo.client.request.AddDataRequest;
import com.quiterr.spring.FeignService;
import com.quiterr.spring.StormContext;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Huangchen on 2017/5/10.
 */
public class ExclamationBolt extends BaseRichBolt {

    public static Logger logger = LoggerFactory.getLogger(ExclamationBolt.class);

    FeignService feignService;
    AddDataRequest addDataRequest;

    OutputCollector _collector;

    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
        StormContext stormContext = StormContext.createApplicationContext();
        feignService= stormContext.getBean(FeignService.class);
        addDataRequest = new AddDataRequest();
        addDataRequest.setDataId("test001");
        DeviceData deviceData = new DeviceData();
        deviceData.setDeviceId("123456");
        deviceData.setTimestamp(123456L);
        deviceData.setData("hello world.");
        addDataRequest.setData(JSON.toJSONString(deviceData));
    }

    public void execute(Tuple tuple) {
        feignService.addDeviceDatas("0224bbcd-1bc9-4bbf-8828-65aa859ccfb5",addDataRequest);
        logger.info("Insert one data.");
        _collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

}
