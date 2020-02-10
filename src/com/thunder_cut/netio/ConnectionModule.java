/*
 * ConnectionModule.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * ConnectionModule is a class that supervises net i/o.
 */
public class ConnectionModule {

    private static ConnectionModule connectionModule;

    private static Socket socket;
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private DataReceiver receiver;
    private Thread receivingThread;

    private Consumer<DataWrapper> senderConsumer;

    /**
     * This is the constructor of class ConnectionModule.
     * <p>
     * object ConnectionModule should be created in <code>main</code>
     */
    private ConnectionModule() {
        try {
            ConnectionModule.socket = new Socket("whiteboard.sysbot32.com", 3001);
            ConnectionModule.inputStream = socket.getInputStream();
            ConnectionModule.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        receiver = new DataReceiver(inputStream);
        receivingThread = new Thread(receiver);
        receivingThread.start();
    }

    /**
     * Is called at
     * <p>
     * - mouseReleased() method, which is in constructor of DrawingCanvas class
     * <p>
     * - undo/redo method
     * @param data is data ready to be sent.
     */
    public void send(DataWrapper data, DataType dataType) {
        try {
            outputStream.write(data.wrappedData.array());
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionModule getInstance() {
        if (connectionModule == null) {
            connectionModule = new ConnectionModule();
        }
        return connectionModule;
    }
}
