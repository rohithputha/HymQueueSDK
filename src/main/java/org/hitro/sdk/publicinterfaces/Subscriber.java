package org.hitro.sdk.publicinterfaces;

import java.util.List;

public interface Subscriber<T,V> {
    public T execute(List<V> data);
}
