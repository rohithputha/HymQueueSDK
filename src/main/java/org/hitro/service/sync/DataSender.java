package org.hitro.service.sync;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
public class DataSender {

    private Socket socket;
    public DataSender(Socket socket){
        this.socket = socket;
    }
    public <T> void sendData(List<T> data) throws IOException {
        byte[] encodedData = IBinaryProtocol.getInstance().encode(data);
        for(int i=0;i<encodedData.length;i++){
            System.out.print(encodedData[i]+"- ");
        }
        System.out.println(IBinaryProtocol.getInstance().decode(encodedData));
        synchronized (socket){
            OutputStream outputStream = socket.getOutputStream();
            int bytesToSend = encodedData.length;
            int offset=0;
            while (bytesToSend > 0) {
                int bytesToWrite = Math.min(21, bytesToSend); // Write up to 21 bytes at a time
                outputStream.write(encodedData, offset, bytesToWrite);
                outputStream.flush(); // Ensure immediate transmission

                offset += bytesToWrite;
                bytesToSend -= bytesToWrite;
                System.out.println(bytesToSend);
            }


        }
    }

}
