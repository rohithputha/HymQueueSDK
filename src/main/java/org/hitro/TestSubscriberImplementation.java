package org.hitro;

import org.hitro.sdk.publicinterfaces.Subscriber;

import java.util.List;

public class TestSubscriberImplementation implements Subscriber {
    @Override
    public Object execute(List data) {
        System.out.println("got the data....");
        System.out.println(data);
        return null;
    }
}
