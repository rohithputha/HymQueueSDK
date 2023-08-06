package org.hitro.service;

import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;

public class HymQueueAdapter implements HymQueue {
    private final HymQueueSdkConfiguration hymQueueSdkConfiguration;
    public HymQueueAdapter(HymQueueSdkConfiguration hymQueueSdkConfiguration){
        this.hymQueueSdkConfiguration = hymQueueSdkConfiguration;
        SubscriberServer.startServer();

    }

    @Override
    public boolean createChannel() {
        return false;
    }

    @Override
    public <T> boolean addData() {
        return false;
    }

    @Override
    public boolean addSubscriber() {
        return false;
    }

    @Override
    public <T> T getData() {
        return null;
    }
}
