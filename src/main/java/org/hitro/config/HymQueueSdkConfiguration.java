package org.hitro.config;

import lombok.AllArgsConstructor;

import java.net.InetAddress;
import java.net.UnknownHostException;


public abstract class HymQueueSdkConfiguration {

    protected String channelName;
    public String getIPAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
    public int getSubscriberListeningPort(){
        return 3457;
    }
    public abstract String getChannelType();
}
