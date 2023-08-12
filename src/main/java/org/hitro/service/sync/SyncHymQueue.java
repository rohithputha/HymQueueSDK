package org.hitro.service.sync;

import org.hitro.config.HymPollQueueSdkConfiguation;
import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;
import org.hitro.service.SocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SyncHymQueue implements HymQueue {

    private final HymQueueSdkConfiguration hymQueueSdkConfiguration;
    private final Socket socket;

    private final DataSender dataSender;
    private final DataReceiver dataReceiver;
    private SyncHymQueue(HymQueueSdkConfiguration hymQueueSdkConfiguration, Socket socket, DataSender dataSender, DataReceiver dataReceiver){
        this.hymQueueSdkConfiguration = hymQueueSdkConfiguration;
        this.socket = socket;
        this.dataSender = dataSender;
        this.dataReceiver = dataReceiver;
    }

    @Override
    public boolean createChannel() {
        List<String> command = Arrays.asList("create_channel","sdk123","testChannel1","poll","\\w");
        try {
            dataSender.sendData(command);
            System.out.println("data sent...");
            System.out.println(dataReceiver.getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public <T> boolean addData(T data) {
        List<Object> command = Arrays.asList("add_data","sdk123",(T)data,"testChannel1","poll","\\w");
        try{
            dataSender.sendData(command);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean addSubscriber() {
        return false;
    }

    @Override
    public <T> T getData() {
        return null;
    }

    public static SyncHymQueue getInstance(HymQueueSdkConfiguration hymQueueSdkConfiguration){
        synchronized (SyncHymQueue.class){
            Socket socket = SocketFactory.getSocket();
            return new SyncHymQueue(hymQueueSdkConfiguration, socket, new DataSender(socket),new DataReceiver(socket));
        }
    }
}
