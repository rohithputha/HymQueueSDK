package org.hitro.sdk.publicinterfaces;

public interface HymQueue {
    public boolean createChannel();

    public <T> boolean addData(T data);

    public boolean addSubscriber(Subscriber subscriber) throws InterruptedException;

    public <T> T getData();
}
