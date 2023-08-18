package org.hitro.config;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class HymPubSubQueueSdkConfiguration extends HymQueueSdkConfiguration{
    public HymPubSubQueueSdkConfiguration(String channelName){
        this.channelName = channelName;
    }
    @Override
    public String getChannelType() {
        return "pubsub";
    }
}
