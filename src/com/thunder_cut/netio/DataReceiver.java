/*
 * ParticipantsImageReceiver.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.EnumMap;
import java.util.Map;
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

    private enum HeaderItem {
        TYPE,
        SRC_ID,
        DST_ID,
        DATA_SIZE;
    }

    @Override
    public void run() {
        while (true) {
            receiveData();
        }
    }

    public void receiveData() {

        try {
            Map<HeaderItem, Integer> header = readHeader();
            readData(header);
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

        drawImage.accept(srcID, receivedData.data.array());
    }

    public void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        this.drawImage = drawImage;
    }

    public DataUnwrapper getReceivedData() {
        return receivedData;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
