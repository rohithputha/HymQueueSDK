package org.hitro.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HymPubSubQueueSdkConfiguration extends HymQueueSdkConfiguration{
    @Override
    public String getChannelType() {
        return "PUBSUB";
    }
}
