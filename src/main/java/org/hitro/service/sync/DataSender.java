package org.hitro.service.sync;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
import org.hitro.config.Constants;
import org.hitro.service.SocketFactory;

public class DataSender<T> implements Runnable{

    private BlockingQueue<List<T>> dataQueue;
    public DataSender(){
        dataQueue = new ArrayBlockingQueue<>(10);
    }
    public void readQueueAndPushData() throws InterruptedException, IOException {
        while(true){
            List<T> data = this.dataQueue.take();
            Socket socket = SocketFactory.getSocket();

            byte[] encodedData = IBinaryProtocol.getInstance().encode(data);
            byte[] commandSep = Constants.getCommandSeparator();
            byte[] combinedArray = new byte[encodedData.length + commandSep.length];
            System.arraycopy(encodedData, 0, combinedArray, 0, encodedData.length);
            System.arraycopy(commandSep, 0, combinedArray, encodedData.length, commandSep.length);

            synchronized (socket){
                OutputStream outputStream = socket.getOutputStream();
                int bytesToSend = combinedArray.length;
                int offset=0;
                while (bytesToSend > 0) {
                    int bytesToWrite = Math.min(25, bytesToSend);
                    outputStream.write(combinedArray, offset, bytesToWrite);
                    outputStream.flush();

                    offset += bytesToWrite;
                    bytesToSend -= bytesToWrite;
                }
            }
        }

    }
    public void sendData(List<T> data) throws InterruptedException {
        this.dataQueue.put(data);
    }
    @Override
    public void run() {
        try {
            readQueueAndPushData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
