package org.hitro.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SubscriberServer implements Runnable {
    private final int port;
    private SubscriberServer(int port){
        this.port = port;
    }

    private void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(this.port);
        while(true){
            Socket socket = serverSocket.accept();
            new Thread(new SubscriberConnection(socket)).start();
        }
    }

    @Override
    public void run() {
        try {
            this.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Thread subServerThread = null;
    public static void startServer(){
        if(subServerThread==null){
            synchronized (SubscriberServer.class){
                if(subServerThread==null){
                    subServerThread = new Thread(new SubscriberServer(3457));
                    subServerThread.start();
                }
            }
        }
    }
}
