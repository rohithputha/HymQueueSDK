package org.hitro.service.sync;

import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;
import org.hitro.sdk.publicinterfaces.Subscriber;
import org.hitro.service.DataInterface;
import java.util.Arrays;
import java.util.List;

public class SyncHymQueue implements HymQueue {
    private final HymQueueSdkConfiguration hymQueueSdkConfiguration;
    private SyncHymQueue(HymQueueSdkConfiguration hymQueueSdkConfiguration){
        this.hymQueueSdkConfiguration = hymQueueSdkConfiguration;
    }

    @Override
    public boolean createChannel() {
        List<String> command = Arrays.asList("create_channel","sdk123","testChannel1","pubsub");
        try {
            DataInterface.getDataInterface().getDataSender().sendData(command);
            System.out.println("data sent...");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public <T> boolean addData(T data) {
        List<Object> command = Arrays.asList("add_data","sdk1234",(T)data,"testChannel1","pubsub");
        try{
            DataInterface.getDataInterface().getDataSender().sendData(command);
//            System.out.println(dataReceiver.getData());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean addSubscriber(Subscriber subscriber) throws InterruptedException {
        if(this.hymQueueSdkConfiguration.getChannelType().equals("pubsub")){
            SubscriberNotificationReceiver.getInstance().addSubscriberCallback("testChannel1",subscriber);
            DataInterface.getDataInterface().getDataSender().sendData(Arrays.asList("add_subscriber","sdk123456","subid123","testChannel1","localhost","3456"));
        }
        return false;
    }

    @Override
    public <T> T getData() {
        try {
            DataInterface.getDataInterface().getDataSender().sendData(Arrays.asList("get_data","sdk12345","testChannel1"));

        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static SyncHymQueue getInstance(HymQueueSdkConfiguration hymQueueSdkConfiguration){
        synchronized (SyncHymQueue.class){
            return new SyncHymQueue(hymQueueSdkConfiguration);
        }
    }
}
