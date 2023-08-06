package org.hitro.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SubDataQueue {
    private final Queue<byte[]> dataQueue;

    private SubDataQueue() {
        this.dataQueue = new LinkedList<>();
    }

    public synchronized void add(byte[] data){
        this.dataQueue.offer(data);
    }
    public synchronized byte[] get() {
        return this.dataQueue.poll();
    }

    private static SubDataQueue subDataQueue;
    public static SubDataQueue getInstance(){
        if(subDataQueue==null){
            synchronized(SubDataQueue.class){
                if(subDataQueue == null){
                    subDataQueue = new SubDataQueue();
                }
            }
        }
        return subDataQueue;
    }
}
