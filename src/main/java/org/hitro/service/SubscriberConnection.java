package org.hitro.service;

import org.hitro.config.Constants;
import org.hitro.dto.SubDataQueue;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SubscriberConnection implements Runnable{

    private final Socket socket;

    public SubscriberConnection(Socket socket){
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
    private void addDataToQueue(byte[] data){
        SubDataQueue.getInstance().add(data);
    }
    private void listenForData(){
        try {
            InputStream inputStream = socket.getInputStream();
            List<Byte> inputCommandsByteList = new ArrayList<>();
            int byteRead;
            while((byteRead = inputStream.read())!=-1){
                inputCommandsByteList.add((byte)byteRead);
                if(inputCommandsByteList.size()>2 &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-2)== Constants.getCommandSeparator()[0] &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-1)==Constants.getCommandSeparator()[1]
                ){
                    byte[] data = this.convertToByteArray(inputCommandsByteList);
                    inputCommandsByteList = new ArrayList<>();
                    this.addDataToQueue(data);
                }
            }
        }
        catch (IOException e) {

        }
    }
    @Override
    public void run() {
        this.listenForData();
    }

}
