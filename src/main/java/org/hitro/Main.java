package org.hitro;

import org.hitro.config.HymPollQueueSdkConfiguation;
import org.hitro.config.HymQueueSdkConfiguration;
import org.hitro.sdk.publicinterfaces.HymQueue;
import org.hitro.service.sync.SyncHymQueue;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        HymQueueSdkConfiguration hymQueueSdkConfiguration = new HymPollQueueSdkConfiguation("helloChannel");
        HymQueue hymQueue = SyncHymQueue.getInstance(hymQueueSdkConfiguration);

        hymQueue.addData(11D);
        hymQueue.getData();
    }
}