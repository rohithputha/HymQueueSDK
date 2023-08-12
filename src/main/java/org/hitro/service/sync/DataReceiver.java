package org.hitro.service.sync;

import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
import org.hitro.config.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataReceiver {

    private Socket socket;
    public DataReceiver(Socket socket){
        this.socket = socket;
    }

    private byte[] convertToByteArray(List<Byte> byteList){
        byte[] newByteArray = new byte[byteList.size()-2];
        for(int i=0;i< byteList.size()-2;i++){
            newByteArray[i]= byteList.get(i);
        }
        byteList.clear();
        return newByteArray;
    }

    public <T> List<T> getData() throws IOException {
        synchronized (socket){
            InputStream inputStream = socket.getInputStream();
            List<Byte> inputCommandsByteList = new ArrayList<>();
            int byteRead;
            while((byteRead = inputStream.read())!=-1){
                inputCommandsByteList.add((byte)byteRead);
                if(inputCommandsByteList.size()>2 &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-2)== Constants.getCommandSeperator()[0] &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-1)==Constants.getCommandSeperator()[1]
                ){
                    System.out.println(inputCommandsByteList.get(inputCommandsByteList.size()-2));
                    System.out.println(inputCommandsByteList.get(inputCommandsByteList.size()-1));
                    System.out.println(inputCommandsByteList.size());
                    byte[] data = this.convertToByteArray(inputCommandsByteList);
                    System.out.println(data.length);
                    return (List<T>) IBinaryProtocol.getInstance().decode(data);
                }
            }
        }
        return null;
    }
}
