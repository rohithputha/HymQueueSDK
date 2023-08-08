package org.hitro.service.sync;

import org.hitro.config.HymPollQueueSdkConfiguation;
import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;
import org.hitro.service.SocketFactory;

import java.net.Socket;

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
        return false;
    }

    @Override
    public <T> boolean addData() {
        return false;
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
