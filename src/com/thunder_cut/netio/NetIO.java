/*
 * NetIO.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-24
 */
package com.thunder_cut.netio;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetIO {

    private enum HeaderItem {
        TYPE,
        SRC_ID,
        DATA_SIZE
    }

    private static SocketChannel socketChannel;
    private static ExecutorService receiver;

    public static void createConnection(String address, int port) {

        if (Objects.isNull(socketChannel) || !socketChannel.isOpen()) {
            try {
                socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(address, port));
                showPlainDialog(address + "에 연결했습니다.");
            } catch (IOException | UnresolvedAddressException e) {
                showWarningDialog("잘못된 주소입니다.");
                return;
            }

            receiver = Executors.newSingleThreadExecutor();
            receiver.submit(NetIO::receive);
        }
        else {
            showWarningDialog("이미 서버에 연결되어 있습니다.");
        }
    }

    public static void destroyConnection() {

        if (Objects.nonNull(socketChannel) && socketChannel.isOpen()) {
            try {
                receiver.shutdownNow();
                socketChannel.close();
                showPlainDialog("서버와의 연결을 해제했습니다.");
            } catch (IOException | NullPointerException e) {
                showWarningDialog("서버와 연결되어 있지 않습니다.");
            }
        }
        else {
            showWarningDialog("서버와 연결되어 있지 않습니다.");
        }
    }

    public static void send(ByteBuffer buffer) {

        if (Objects.nonNull(socketChannel) && socketChannel.isConnected()) {
            try {
                socketChannel.write(buffer);
            } catch (IOException e) {
                showWarningDialog("서버에 연결되어 있지 않습니다.");
            }
        }
    }

    public static void receive() {

        while (!Thread.currentThread().isInterrupted()) {

            try {
                Map<HeaderItem, Integer> header = readHeader();
                DataHandler.handleData((char) header.get(HeaderItem.TYPE).intValue(), header.get(HeaderItem.SRC_ID), readData(header.get(HeaderItem.DATA_SIZE)));

            } catch (ClosedByInterruptException e) {
                Thread.currentThread().interrupt();

            } catch (IOException | NullPointerException e) {
                showPlainDialog("서버에서 추방당했습니다.");
                destroyConnection();
            }
        }
    }

    private static ByteBuffer readData(int dataSize) throws IOException {

        ByteBuffer data = ByteBuffer.allocate(dataSize);
        while (data.hasRemaining()) {
            socketChannel.read(data);
        }
        data.flip();

        return data;
    }

    private static Map<HeaderItem, Integer> readHeader() throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(10);
        socketChannel.read(buffer);
        buffer.flip();

        return makeHeaderItem(buffer);
    }

    private static Map<HeaderItem, Integer> makeHeaderItem(ByteBuffer buffer) {

        Map<HeaderItem, Integer> header = new EnumMap<>(HeaderItem.class);
        try {
            header.put(HeaderItem.TYPE, (int) buffer.getChar());
            header.put(HeaderItem.SRC_ID, buffer.getInt());
            header.put(HeaderItem.DATA_SIZE, buffer.getInt());
        } catch (BufferUnderflowException e) {
            header = null;
        }

        return header;
    }

    private static void showPlainDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.PLAIN_MESSAGE);
    }

    private static void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.WARNING_MESSAGE);
    }
}
