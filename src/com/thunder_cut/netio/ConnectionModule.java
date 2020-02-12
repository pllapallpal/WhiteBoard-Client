/*
 * ConnectionModule.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.function.BiConsumer;

/**
 * ConnectionModule is a class that supervises net i/o.
 */
public class ConnectionModule {

    private static ConnectionModule connectionModule;

    private SocketChannel socketChannel;
    private DataReceiver receiver;
    private Thread receivingThread;

    /**
     * This is the constructor of class ConnectionModule.
     * <p>
     * object ConnectionModule should be created in <code>main</code>
     */
    private ConnectionModule() {

        receiver = new DataReceiver();
    }

    public static void initialize() {
        if (connectionModule == null) {
            connectionModule = new ConnectionModule();
        }
    }

    public static void createConnection() {
        if (connectionModule == null) {
            initialize();
        }
        try {
            connectionModule.socketChannel = SocketChannel.open();
            connectionModule.socketChannel.connect(new InetSocketAddress("127.0.0.1", 3001));
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionModule.receiver.setSocketChannel(connectionModule.socketChannel);
        connectionModule.receivingThread = new Thread(connectionModule.receiver);
        connectionModule.receivingThread.start();
    }

    public static void send(BufferedImage image) {
        send(new DataWrapper(image));
    }

    /**
     * Is called at
     * <p>
     * - mouseReleased() method, which is in constructor of DrawingCanvas class
     * <p>
     * - undo/redo method
     * @param data is data ready to be sent.
     */
    public static void send(DataWrapper data) {
        try {
            connectionModule.socketChannel.write(data.wrappedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        connectionModule.receiver.addDrawImage(drawImage);
    }
}
