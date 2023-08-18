package org.hitro;

import org.hitro.config.HymPollQueueSdkConfiguation;
import org.hitro.config.HymPubSubQueueSdkConfiguration;
import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;
import org.hitro.sdk.publicinterfaces.Subscriber;
import org.hitro.service.sync.SyncHymQueue;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
//        HymQueueSdkConfiguration hymQueueSdkConfiguration = new HymPollQueueSdkConfiguation("helloChannel");
        HymQueueSdkConfiguration hymQueueSdkConfiguration = new HymPubSubQueueSdkConfiguration("helloChannel");
        HymQueue hymQueue = SyncHymQueue.getInstance(hymQueueSdkConfiguration);

        Subscriber subscriber = new TestSubscriberImplementation();
//        hymQueue.createChannel();
        hymQueue.addSubscriber(subscriber);
        hymQueue.addData(100D);
    }
}