package org.hitro.sdk.publicinterfaces;

import java.util.List;
import java.util.UUID;

public interface HymQueue {
    public boolean createChannel();

    public <T> boolean addData(T data);

    public boolean addSubscriber(Subscriber subscriber) throws InterruptedException;

    public <T> List<T> getData();

    default String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
