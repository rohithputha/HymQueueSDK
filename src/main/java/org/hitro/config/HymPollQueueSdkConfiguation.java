package org.hitro.config;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class HymPollQueueSdkConfiguation extends HymQueueSdkConfiguration{
    public HymPollQueueSdkConfiguation(String channelName){
        this.channelName = channelName;
    }
    @Override
    public String getChannelType() {
        return "poll";
    }
}
