package org.hitro.service.sync;

import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
import org.hitro.config.Constants;
import org.hitro.service.SocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataReceiver implements Runnable{
    private byte[] convertToByteArray(List<Byte> byteList){
        byte[] newByteArray = new byte[byteList.size()-2];
        for(int i=0;i< byteList.size()-2;i++){
            newByteArray[i]= byteList.get(i);
        }
        byteList.clear();
        return newByteArray;
    }

    public <T> void  listenInputStream() throws IOException, InterruptedException {
        Socket socket = SocketFactory.getSocket();
        InputStream inputStream =null;
        synchronized (socket){
           inputStream  = socket.getInputStream();
        }

        List<Byte> inputCommandsByteList = new ArrayList<>();
        int byteRead;
        while((byteRead = inputStream.read())!=-1){
            inputCommandsByteList.add((byte)byteRead);
            if(inputCommandsByteList.size()>2 &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-2)== Constants.getCommandSeparator()[0] &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-1)==Constants.getCommandSeparator()[1]
            ){
                    byte[] data = this.convertToByteArray(inputCommandsByteList);
                    System.out.println(data.length);
                    ResposeHolder.getInstance().addResponse((List<T>) IBinaryProtocol.getInstance().decode(data));
            }
        }
        System.out.println("done with response listening");
    }

    @Override
    public void run() {
        try {
            listenInputStream();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
