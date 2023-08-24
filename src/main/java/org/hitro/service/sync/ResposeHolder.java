package org.hitro.service.sync;

import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ResposeHolder<T> {
    private Map<String, List<T>> commandIdResponseMap;
    @Getter
    private BlockingQueue<List<T>> subscriberNotifications;
    private ResposeHolder(){
        commandIdResponseMap = new ConcurrentHashMap<>();
        subscriberNotifications = new ArrayBlockingQueue<>(10);
    }

    public void addResponse(List<T> resp) throws InterruptedException {
        System.out.println("response .."+resp);
        if(resp.size()>=2 &&  ((String)resp.get(1)).equals("subscriberCallback")){
            subscriberNotifications.put(resp);
        }
        else{
            commandIdResponseMap.put((String)resp.get(0),resp);
        }
    }
    public List<T> getResponse(String commandId){
        if(commandIdResponseMap.containsKey(commandId)){
            List<T> res = commandIdResponseMap.get(commandId);
            commandIdResponseMap.remove(commandId);
            return res;
        }
        return null;
    }
    private static ResposeHolder resposeHolder;
    public static ResposeHolder getInstance(){
        if(resposeHolder==null){
            synchronized (ResposeHolder.class){
                if(resposeHolder==null){
                    resposeHolder= new ResposeHolder();
                }
            }
        }
        return resposeHolder;
    }
}
