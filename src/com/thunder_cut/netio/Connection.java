/*
 * Connection.java
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
 * A class that supervises overall net i/o
 */
public class Connection {

    private static Connection connectionModule;

    private SocketChannel socketChannel;
    private DataReceiver receiver;
    private Thread receivingThread;

    private Connection() {

        receiver = new DataReceiver();
    }

    /**
     * Creates connectionModule instance (if it doesn't exist)
     */
    public static void initialize() {
        if (connectionModule == null) {
            connectionModule = new Connection();
        }
    }

    /**
     * If connectionModule doesn't exist, this method runs initialize() method
     * <p>
     * Opens socketChannel, and starts to receive data from the server
     */
    public static void createConnection(String address, int port) {
        if (connectionModule == null) {
            initialize();
        }
        try {
            connectionModule.socketChannel = SocketChannel.open();
            connectionModule.socketChannel.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionModule.receiver.setSocketChannel(connectionModule.socketChannel);
        connectionModule.receivingThread = new Thread(connectionModule.receiver);
        connectionModule.receivingThread.start();
    }

    /**
     * Is called at drawCanvas() method in DrawingCanvas
     *
     * @param image is wrapped by overridden send() method
     */
    public static void send(BufferedImage image) {
        send(new DataWrapper(image));
    }

    /**
     * Sends data through the socketChannel
     *
     * @param data Data that is ready to be sent
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
