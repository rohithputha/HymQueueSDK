package org.hitro.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HymPollQueueSdkConfiguation extends HymQueueSdkConfiguration{
    @Override
    public String getChannelType() {
        return "POLL";
    }
}
