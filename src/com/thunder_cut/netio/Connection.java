/*
 * Connection.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;

/**
 * A class that supervises overall net i/o (establish connection, send/receive data)
 */
public class Connection {

    private static Connection connectionModule;

    private SocketChannel socketChannel;

    private DataReceiver receiver;

    private static String nickname;

    private Connection() {

        receiver = new DataReceiver();
    }

    /**
     * Initializes connectionModule instance, and sets temporary nickname
     */
    public static void initialize() {
        if (Objects.isNull(connectionModule)) {
            connectionModule = new Connection();
            setNickname("user" + Integer.toString(ThreadLocalRandom.current().nextInt(65536)));
        }
    }

    /**
     * If connectionModule instance is not initialized, this method runs initialize() method.
     * Then it opens socketChannel, and starts to receive data from the server.
     *
     * @param address Address of the server you want to connect in
     * @param port    Port number that matches with server
     */
    public static void createConnection(String address, int port) {

        if (Objects.isNull(connectionModule)) {
            initialize();
        }

        try {
            connectionModule.socketChannel = SocketChannel.open();
            connectionModule.socketChannel.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionModule.receiver.addReadSocket(Connection::readSocket);
        connectionModule.receiver.start();

        send(ChatCommands.SET_NAME.command + getNickname());
    }

    /**
     * Stop receiving data and close a socketChannel.
     */
    public static void destroyConnection() {

        if (Objects.isNull(connectionModule)) {
            return;
        }

        if (connectionModule.receiver.getIsReceiving()) {
            connectionModule.receiver.stop();
        }
        if (Objects.nonNull(connectionModule.socketChannel) && connectionModule.socketChannel.isOpen()) {
            try {
                connectionModule.socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        connectionModule = null;
    }

    /**
     * Sends data to connected server.
     *
     * @param data Data ready to be sent
     */
    public static void send(EncapsulatedData data) {
        if (Objects.isNull(connectionModule) || Objects.isNull(connectionModule.socketChannel)) {
            return;
        }
        try {
            connectionModule.socketChannel.write(data.encapsulatedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends image to connected server.
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
        if (message.charAt(0) == '/') {
            send(new EncapsulatedData(ByteBuffer.wrap(message.getBytes()), DataType.CMD));
        } else {
            send(new EncapsulatedData(message));
        }
    }

    /**
     * Functional interface, mediates drawImage() in ParticipantsPanel to DataReceiver.
     *
     * @param drawImage This is a BiConsumer.
     *                  First argument Integer stands for srcID,
     *                  second argument byte[] stands for the image transformed to byte array
     */
    public static void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        connectionModule.receiver.addDrawImage(drawImage);
    }

    /**
     * Functional interface, mediates receiveMessage() in ChatFrame to DataReceiver.
     *
     * @param receiveMessage This is a BiConsumer.
     *                       First argument Integer stands for srcID,
     *                       second argument byte[] is bytes got from the message
     */
    public static void addReceiveMessage(BiConsumer<Integer, byte[]> receiveMessage) {
        connectionModule.receiver.addReceiveMessage(receiveMessage);
    }

    /**
     * Reads ByteBuffer from the socket.
     *
     * @param buffer Where the received data is stored.
     */
    public static void readSocket(ByteBuffer buffer) {
        try {
            connectionModule.socketChannel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Nickname of you
     */
    public static String getNickname() {
        return Connection.nickname;
    }

    /**
     * @param nickname Nickname you want to set by
     */
    public static void setNickname(String nickname) {
        Connection.nickname = nickname;
    }
}
