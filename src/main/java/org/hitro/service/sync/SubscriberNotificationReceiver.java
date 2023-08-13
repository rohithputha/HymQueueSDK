package org.hitro.service.sync;

import org.hitro.sdk.publicinterfaces.Subscriber;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscriberNotificationReceiver<T> implements Runnable{
    private ResposeHolder resposeHolder;
    private Map<String, Subscriber> channelSubscriberMap;
    private SubscriberNotificationReceiver(){
        this.resposeHolder = ResposeHolder.getInstance();
        channelSubscriberMap = new ConcurrentHashMap<>();
    }
    public void addSubscriberCallback(String channelName, Subscriber subscriber){
        this.channelSubscriberMap.put(channelName,subscriber);
    }

    public void listenToNotifications() throws InterruptedException {
        while(true){
            Object data = resposeHolder.getSubscriberNotifications().take();
            List<T> listData = (List<T>) data;
            channelSubscriberMap.get((String)listData.get(2)).execute(listData);
        }
    }
    @Override
    public void run() {
        try {
            listenToNotifications();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
