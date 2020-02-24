/*
 * DataReceiver.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import java.nio.ByteBuffer;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Receives data sent from the server, looping until the program ends.
 */
public class DataReceiver {

    private enum HeaderItem {
        TYPE,
        SRC_ID,
        DST_ID,
        DATA_SIZE;
    }

    private Consumer<ByteBuffer> readSocket;

    private BiConsumer<Integer, byte[]> drawImage;
    private BiConsumer<Integer, byte[]> receiveMessage;

    private ExecutorService receivingExecutor;
    private AtomicBoolean isReceiving;
    private Future<?> futureReceivingExecutor;

    public DataReceiver() {
        receivingExecutor = Executors.newSingleThreadExecutor();
        isReceiving = new AtomicBoolean();
    }

    public void start() {
        isReceiving.set(true);
        futureReceivingExecutor = receivingExecutor.submit(this::startReceiving);
    }

    public void stop() {
        isReceiving.set(false);
        futureReceivingExecutor.cancel(true);
        receivingExecutor.shutdown();
    }

    public boolean getIsReceiving() {
        return isReceiving.get();
    }

    public void startReceiving() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                if (!isReceiving.get()) {
                    break;
                }
            }
            DecapsulatedData decapsulatedData = receiveData();

            if (decapsulatedData.dataType == DataType.IMG.type) {
                drawImage.accept(decapsulatedData.srcID, decapsulatedData.data.array());
            }
            else {
                receiveMessage.accept(decapsulatedData.srcID, decapsulatedData.data.array());
            }
        }
    }

    private DecapsulatedData receiveData() {

        Map<HeaderItem, Integer> header = readHeader();
        int dataSize = header.get(HeaderItem.DATA_SIZE);
        int srcID = header.get(HeaderItem.SRC_ID);

        ByteBuffer data = ByteBuffer.allocate(dataSize);
        while (data.hasRemaining()) {
            readSocket.accept(data);
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

    private Map<HeaderItem, Integer> readHeader() {

        Map<HeaderItem, Integer> header = new EnumMap<>(HeaderItem.class);
        ByteBuffer headerBuffer = ByteBuffer.allocate(14);
        readSocket.accept(headerBuffer);

        headerBuffer.flip();

        header.put(HeaderItem.TYPE, (int) headerBuffer.getChar());
        header.put(HeaderItem.SRC_ID, headerBuffer.getInt());
        header.put(HeaderItem.DST_ID, headerBuffer.getInt());
        header.put(HeaderItem.DATA_SIZE, headerBuffer.getInt());

        return header;
    }

    /**
     * Functional interface, adds drawImage() (accepted from Connection) to this (DataReceiver).
     *
     * @param drawImage DrawImage is BiConsumer.
     *                  First argument Integer stands for srcID,
     *                  second argument byte[] is image transformed to byte array
     */
    public void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        this.drawImage = drawImage;
    }

    /**
     * Functional interface, adds receiveMessage() to this (dataReceiver).
     *
     * @param receiveMessage ReceiveMessage is BiConsumer.
     *                       First argument Integer stands for srcID,
     *                       second argument byte[] is bytes got from the message
     */
    public void addReceiveMessage(BiConsumer<Integer, byte[]> receiveMessage) {
        this.receiveMessage = receiveMessage;
    }

    /**
     * Functional interface, adds readSocket() to this (dataReceiver).
     *
     * @param readSocket ReadSocket is Consumer.
     *                   Argument ByteBuffer is a buffer where the received data is stored.
     */
    public void addReadSocket(Consumer<ByteBuffer> readSocket) {
        this.readSocket = readSocket;
    }
}
