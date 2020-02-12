/*
 * ConnectionModule.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * ConnectionModule is a class that supervises net i/o.
 */
public class ConnectionModule {

    private static ConnectionModule connectionModule;

    private SocketChannel socketChannel;
    public final DataReceiver receiver;
    public final Thread receivingThread;

    /**
     * This is the constructor of class ConnectionModule.
     * <p>
     * object ConnectionModule should be created in <code>main</code>
     */
    private ConnectionModule() {

        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 3001));
        } catch (IOException e) {
            e.printStackTrace();
        }

        receiver = new DataReceiver(socketChannel);
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
    public void send(DataWrapper data) {
        try {
            socketChannel.write(data.wrappedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionModule getInstance() {
        if (connectionModule == null) {
            connectionModule = new ConnectionModule();
        }
        return connectionModule;
    }
}
