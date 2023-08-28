package org.hitro.service;

import lombok.Getter;
import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

@Getter
public class SocketFactory {


    private static Socket getConnectedSocket(){
        try {
//            String url = "hym-queue-ws-ma6te.ondigitalocean.app";
//            InetAddress[] addresses = InetAddress.getAllByName(url);
//
//            for (InetAddress address : addresses) {
//                System.out.println("Resolved IP Address: " + address.getHostAddress());
//            }

            return new Socket("159.89.197.174", 3456);
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
                   try {
                       DataInterface.getDataInterface().getDataSender().sendData(Arrays.asList("register_socket","sd1324234","localhost"));
                       //socket.getOutputStream().write(IBinaryProtocol.getInstance().encode(Arrays.asList("register_socket","sd1324234","localhost")));
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }
               }
            }
        }
        return socket;
    }
}
