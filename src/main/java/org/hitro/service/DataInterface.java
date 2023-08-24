package org.hitro.service;

import lombok.Getter;
import org.hitro.service.sync.DataReceiver;
import org.hitro.service.sync.DataSender;

@Getter
public class DataInterface {
    private final DataSender dataSender;
    private final DataReceiver dataReceiver;
    private Thread dsThread;
    private Thread drThread;

    private DataInterface(DataSender dataSender, DataReceiver dataReceiver){
        this.dataSender = dataSender;
        this.dataReceiver = dataReceiver;
        new Thread(dataSender).start();
        new Thread(dataReceiver).start();
    }

    private static DataInterface dataInterface;
    public static DataInterface getDataInterface(){
        if(dataInterface== null){
            synchronized (DataInterface.class){
                if(dataInterface==null){
                    DataSender dataSender1 = new DataSender();
                    DataReceiver dataReceiver1 = new DataReceiver();
                    dataInterface  = new DataInterface(dataSender1,dataReceiver1);
                }
            }
        }
        return dataInterface;
    }
}
