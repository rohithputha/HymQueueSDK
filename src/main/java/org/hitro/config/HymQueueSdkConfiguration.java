package org.hitro.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.InetAddress;
import java.net.UnknownHostException;


public abstract class HymQueueSdkConfiguration {

    @Getter
    protected String channelName;
    public String getIPAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
    public int getSubscriberListeningPort(){
        return 3457;
    }
    public abstract String getChannelType();
}
