package org.hitro.sdk.publicinterfaces;

public interface HymQueue {
    public boolean createChannel();

    public <T> boolean addData();

    public boolean addSubscriber();

    public <T> T getData();
}
