package org.hitro.service.sync;

import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;
import org.hitro.sdk.publicinterfaces.Subscriber;
import org.hitro.service.DataInterface;
import org.hitro.service.SocketFactory;

import java.util.Arrays;
import java.util.List;

public class SyncHymQueue implements HymQueue {
    private final HymQueueSdkConfiguration hymQueueSdkConfiguration;

    private final ResposeHolder resposeHolder;
    private SyncHymQueue(HymQueueSdkConfiguration hymQueueSdkConfiguration, ResposeHolder resposeHolder){
        this.hymQueueSdkConfiguration = hymQueueSdkConfiguration;
        this.resposeHolder = resposeHolder;
    }

    private <T> List<T> getResponse(String uuid) throws InterruptedException {
        List<T> resp = null;
        int count = 0;
        do{
            resp = this.resposeHolder.getResponse(uuid);
            if(resp!=null || count>100) return resp;
            count++;
            Thread.sleep(100);
        }
        while (resp == null);
        return null;
    }

    @Override
    public boolean createChannel() {

        try {
            String uuid = getUUID();
            List<String> command = Arrays.asList("create_channel",uuid,hymQueueSdkConfiguration.getChannelName(),hymQueueSdkConfiguration.getChannelType());
            DataInterface.getDataInterface().getDataSender().sendData(command);
            String resp = (String) getResponse(uuid).get(1);
            if((resp).equals("SUCCESS")) {
                return true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public <T> boolean addData(T data) {
        String uuid = getUUID();
        List<Object> command = Arrays.asList("add_data",uuid,(T)data,hymQueueSdkConfiguration.getChannelName(),hymQueueSdkConfiguration.getChannelType());
        try{
            DataInterface.getDataInterface().getDataSender().sendData(command);
            String resp = (String) getResponse(uuid).get(1);
            if((resp).equals("SUCCESS")) {
                return true;
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean addSubscriber(Subscriber subscriber) throws InterruptedException {
        if(this.hymQueueSdkConfiguration.getChannelType().equals("pubsub")){
            SubscriberNotificationReceiver.getInstance().addSubscriberCallback(hymQueueSdkConfiguration.getChannelName(), subscriber);
            String uuid = getUUID();
            DataInterface.getDataInterface().getDataSender().sendData(Arrays.asList("add_subscriber",uuid,"localhost"+hymQueueSdkConfiguration.getChannelName(),hymQueueSdkConfiguration.getChannelName(),"localhost","3456"));
            String resp = (String) getResponse(uuid).get(1);
            if((resp).equals("SUCCESS")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> List<T> getData() {
        try {
            String uuid = getUUID();
            DataInterface.getDataInterface().getDataSender().sendData(Arrays.asList("get_data",uuid,hymQueueSdkConfiguration.getChannelName()));
            return getResponse(uuid);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static SyncHymQueue getInstance(HymQueueSdkConfiguration hymQueueSdkConfiguration){
        synchronized (SyncHymQueue.class){
            SocketFactory.getSocket();
            ResposeHolder resposeHolder1 = ResposeHolder.getInstance();
            return new SyncHymQueue(hymQueueSdkConfiguration,resposeHolder1);
        }
    }
}
