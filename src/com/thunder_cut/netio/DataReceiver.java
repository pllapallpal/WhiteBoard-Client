/*
 * DataReceiver.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Receives data sent from the server, looping until the program ends
 */
public class DataReceiver implements Runnable {

    private enum HeaderItem {
        TYPE,
        SRC_ID,
        DST_ID,
        DATA_SIZE;
    }

    private SocketChannel socketChannel;

    private BiConsumer<Integer, byte[]> drawImage;

    private Map<Character, BiConsumer<Integer, byte[]>> dataType;

    @Override
    public void run() {

        dataType = new HashMap<>();

        dataType.put(DataType.IMG.type, drawImage);

        // TODO: 탈출 조건 만들기
        //  ExecutorService 로 전환하는 것도 방법
        //  if문 넣고 synchronize 걸기 - 자원관리
        while (true) {
            DecapsulatedData decapsulatedData = receiveData();

            dataType.get(decapsulatedData.dataType).accept(decapsulatedData.srcID, decapsulatedData.data.array());
        }
    }

    private DecapsulatedData receiveData() {

        DecapsulatedData decapsulatedData = null;
        try {
            decapsulatedData = readData(readHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decapsulatedData;
    }

    private DecapsulatedData readData(Map<HeaderItem, Integer> header) throws IOException {

        int dataSize = header.get(HeaderItem.DATA_SIZE);
        int srcID = header.get(HeaderItem.SRC_ID);

        ByteBuffer data = ByteBuffer.allocate(dataSize);
        while (data.hasRemaining()) {
            socketChannel.read(data);
        }
        data.flip();

        ByteBuffer unwrappedData = ByteBuffer.allocate(dataSize + 14);
        unwrappedData.putChar((char) header.get(HeaderItem.TYPE).intValue())
                .putInt(srcID)
                .putInt(header.get(HeaderItem.DST_ID))
                .putInt(dataSize)
                .put(data);

        return new DecapsulatedData(unwrappedData);
    }

    private Map<HeaderItem, Integer> readHeader() throws IOException {

        Map<HeaderItem, Integer> headers = new EnumMap<>(HeaderItem.class);
        ByteBuffer header = ByteBuffer.allocate(14);
        socketChannel.read(header);

        header.flip();

        headers.put(HeaderItem.TYPE, (int) header.getChar());
        headers.put(HeaderItem.SRC_ID, header.getInt());
        headers.put(HeaderItem.DST_ID, header.getInt());
        headers.put(HeaderItem.DATA_SIZE, header.getInt());

        return headers;
    }

    /**
     * Functional interface, adds drawImage() (accepted from Connection) to DataReceiver
     *
     * @param drawImage DrawImage is BiConsumer.
     *                  First argument Integer stands for srcID,
     *                  Second argument byte[] is image transformed to byte array
     */
    public void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        this.drawImage = drawImage;
    }

    /**
     * Sets socketChannel needed to communicate with the server
     *
     * @param socketChannel Sets socketChannel value in DataReceiver
     */
    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
