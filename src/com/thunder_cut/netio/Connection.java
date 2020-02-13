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
 * A class that supervises overall net i/o (establish connection, send/receive data)
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
     * Initializes connectionModule instance
     */
    public static void initialize() {
        if (connectionModule == null) {
            connectionModule = new Connection();
        }
    }

    /**
     * If connectionModule instance doesn't exist, this method
     * runs initialize() method
     * <p>
     * Opens socketChannel, and starts to receive data from the server
     *
     * @param address Address of the server you want to connect in
     * @param port Port number that matches with server
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
     * Sends image to connected server
     *
     * @param image Image to send to the server
     */
    public static void send(BufferedImage image) {
        send(new DataWrapper(image));
    }

    /**
     * Sends data to connected server
     *
     * @param data Data ready to be sent
     */
    public static void send(DataWrapper data) {
        try {
            connectionModule.socketChannel.write(data.wrappedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Functional interface, mediates drawImage() in ParticipantsPanel to DataReceiver
     *
     * @param drawImage DrawImage is BiConsumer.
     *                  First argument Integer stands for srcID,
     *                  Second argument byte[] is image transformed to byte array
     */
    public static void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        connectionModule.receiver.addDrawImage(drawImage);
    }
}
