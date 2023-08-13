package org.hitro.service.sync;

import org.hitro.config.HymPollQueueSdkConfiguation;
import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;
import org.hitro.service.DataInterface;
import org.hitro.service.SocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SyncHymQueue implements HymQueue {

    private final HymQueueSdkConfiguration hymQueueSdkConfiguration;
    private SyncHymQueue(HymQueueSdkConfiguration hymQueueSdkConfiguration){
        this.hymQueueSdkConfiguration = hymQueueSdkConfiguration;
    }

    @Override
    public boolean createChannel() {
        List<String> command = Arrays.asList("create_channel","sdk123","testChannel1","poll");
        try {
            DataInterface.getDataInterface().getDataSender().sendData(command);
            System.out.println("data sent...");
//            System.out.println(dataReceiver.getData());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public <T> boolean addData(T data) {
        List<Object> command = Arrays.asList("add_data","sdk123",(T)data,"testChannel1","poll");
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
    public boolean addSubscriber() {
        return
    }

    @Override
    public <T> T getData() {
        try {
            DataInterface.getDataInterface().getDataSender().sendData(Arrays.asList("get_data","sdk1234","testChannel1"));
//            System.out.println(dataReceiver.getData());
        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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
