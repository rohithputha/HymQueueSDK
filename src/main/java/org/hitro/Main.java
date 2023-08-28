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
        hymQueue.createChannel();
        hymQueue.addSubscriber(subscriber);

        new Thread(()->{
            for (int i=0;i<10000;i++) hymQueue.addData("holad whats up");
        }).start();

        new Thread(()->{
            for (int i=0;i<50000;i++) hymQueue.addData(1020D);
        }).start();

        new Thread(()->{
            for (int i=0;i<50000;i++) hymQueue.addData(1040D);
        }).start();
        Thread.sleep(10000);
    }
}