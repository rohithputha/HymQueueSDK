package org.hitro.service;

import lombok.Getter;

import java.io.IOException;
import java.net.Socket;

@Getter
public class SocketFactory {


    private static Socket getConnectedSocket(){
        try {
            return new Socket("localhost", 3456);
        } catch (IOException e) {
            return null;
        }

    }

    public static Socket getSocket(){
        synchronized (SocketFactory.class){
            return getConnectedSocket();
        }
    }
}
