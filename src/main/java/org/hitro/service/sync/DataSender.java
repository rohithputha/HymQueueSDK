package org.hitro.service.sync;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
public class DataSender {

    private Socket socket;
    public DataSender(Socket socket){
        this.socket = socket;
    }
    public void sendData(List<String> data) throws IOException {
        byte[] encodedData = IBinaryProtocol.getInstance().encode(data);
        synchronized (socket){
            this.socket.getOutputStream().write(encodedData);
        }
    }

}
