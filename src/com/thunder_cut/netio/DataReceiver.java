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
    private DataUnwrapper receivedData;

    private BiConsumer<Integer, byte[]> drawImage;

    private Map<Character, BiConsumer<Integer, byte[]>> dataType;

    @Override
    public void run() {

        dataType = new HashMap<Character, BiConsumer<Integer, byte[]>>();

        dataType.put(DataType.IMG.type, drawImage);

        while (true) {
            receiveData();

            dataType.get(receivedData.dataType).accept(receivedData.srcID, receivedData.data.array());
        }
    }

    private void receiveData() {

        try {
            readData(readHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void readData(Map<HeaderItem, Integer> header) throws IOException {

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

        receivedData = new DataUnwrapper(unwrappedData);
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
