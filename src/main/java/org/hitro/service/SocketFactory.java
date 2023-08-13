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
    private static Socket socket;
    public static Socket getSocket(){
        if(socket== null || socket.isClosed()){
            synchronized (SocketFactory.class){
               if(socket==null || socket.isClosed()){
                   socket =  getConnectedSocket();
               }
            }
        }
        return socket;
    }
}
