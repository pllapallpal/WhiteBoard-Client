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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * A class that supervises overall net i/o (establish connection, send/receive data)
 */
public class Connection {

    private static Connection connectionModule;

    private SocketChannel socketChannel;

    private DataReceiver receiver;
    private ExecutorService receivingExecutorService;
    private boolean isReceiving = false;

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
     * If connectionModule instance is not initialized, this method runs initialize() method.
     * Then it opens socketChannel, and starts to receive data from the server.
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
        connectionModule.receivingExecutorService = Executors.newSingleThreadExecutor();
        startReceiving();
    }

    /**
     * Sends data to connected server
     *
     * @param data Data ready to be sent
     */
    public static void send(EncapsulatedData data) {
        try {
            connectionModule.socketChannel.write(data.encapsulatedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends image to connected server
     *
     * @param image Image to be sent to the server
     */
    public static void send(BufferedImage image) {
        send(new EncapsulatedData(image));
    }

    /**
     * Sends message to connected server.
     * If the message starts with '/', message would work as command.
     *
     * @param message Message to be sent to the server
     */
    public static void send(String message) {
        send(new EncapsulatedData(message));
    }

    /**
     * Functional interface, mediates drawImage() in ParticipantsPanel to DataReceiver
     *
     * @param drawImage This is a BiConsumer.
     *                  First argument Integer stands for srcID,
     *                  second argument byte[] stands for the image transformed to byte array
     */
    public static void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        connectionModule.receiver.addDrawImage(drawImage);
    }

    /**
     * Functional interface, mediates receiveMessage() in ChatFrame to DataReceiver
     *
     * @param receiveMessage This is a BiConsumer.
     *                       First argument Integer stands for srcID,
     *                       second argument byte[] is bytes got from the message
     */
    public static void addReceiveMessage(BiConsumer<Integer, byte[]> receiveMessage) {
        connectionModule.receiver.addReceiveMessage(receiveMessage);
    }

    /**
     * Starts to receive data from the server
     */
    public static void startReceiving() {
        if (!connectionModule.isReceiving) {
            connectionModule.isReceiving = true;
            connectionModule.receivingExecutorService.submit(connectionModule.receiver);
        }
    }

    /**
     * Stops receiving data from the server
     */
    public static void stopReceiving() {
        if (connectionModule.isReceiving) {
            connectionModule.isReceiving = false;
            connectionModule.receivingExecutorService.shutdown();
        }
    }
}
