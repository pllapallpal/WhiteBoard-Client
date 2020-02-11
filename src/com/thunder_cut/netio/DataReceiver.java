/*
 * ParticipantsImageReceiver.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Receives other participants' image
 * <p>
 * It has infinite loop, and is run in constructor of ConnectionModule
 */
public class DataReceiver implements Runnable {

    private SocketChannel socketChannel;
    private DataUnwrapper receivedData;

    private BiConsumer<Integer, byte[]> drawImage;

    public DataReceiver(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        while (true) {
            receiveData();
        }
    }

    public void receiveData() {

        try {
            // 먼저 헤더만큼 allocate 한 다음에 그만큼만 읽는다
            ByteBuffer header = ByteBuffer.allocate(14);
            socketChannel.read(header);

            // 헤더를 딴다
            header.flip();
            char type = header.getChar();
            int srcID = header.getInt();
            int dstID = header.getInt();
            int dataSize = header.getInt();

            System.out.println(type);
            System.out.println(srcID);
            System.out.println(dstID);
            System.out.println(dataSize);

            // dataSize 만큼 ByteBuffer allocate 한 뒤 받는다
            ByteBuffer data = ByteBuffer.allocate(dataSize);
            while (data.hasRemaining()) {
                socketChannel.read(data);
            }
            System.out.println("received " + data.toString());

            // 헤더를 포함한 unwrappedData
            data.flip();
            ByteBuffer unwrappedData = ByteBuffer.allocate(dataSize + 14);
            unwrappedData.putChar(type);
            unwrappedData.putInt(srcID);
            unwrappedData.putInt(dstID);
            unwrappedData.putInt(dataSize);
            unwrappedData.put(data);
            receivedData = new DataUnwrapper(unwrappedData);
            System.out.println("succeed to receive : " + Arrays.toString(unwrappedData.array()));

            drawImage.accept(srcID, receivedData.data.array());

        } catch (IOException e) {
            System.out.println("failed to receive data");
            e.printStackTrace();
        }
    }

    public void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        this.drawImage = drawImage;
    }

    public DataUnwrapper getReceivedData() {
        return receivedData;
    }
}
