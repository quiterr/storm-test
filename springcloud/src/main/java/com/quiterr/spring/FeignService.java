package com.quiterr.spring;

import com.chinamobile.iot.lightapp.mongo.client.MongoWriteDataClient;
import com.chinamobile.iot.lightapp.mongo.client.request.AddDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xuetao on 2017/5/10.
 */
@Component
public class FeignService {
    @Autowired
    MongoWriteDataClient mongoWriteDataClient;

    public boolean addDeviceDatas(String appToken, AddDataRequest addDataRequest){
        return mongoWriteDataClient.addDeviceDatas(appToken,addDataRequest);
    }
}