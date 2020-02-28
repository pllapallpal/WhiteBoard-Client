/*
 * DataHandler.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-24
 */
package com.thunder_cut.netio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.BiConsumer;

/**
 * Refines data into sendable data.
 */
public class DataHandler {

    private static BiConsumer<Integer, byte[]> drawImage;
    private static BiConsumer<Integer, byte[]> receiveMessage;

    public static void send(BufferedImage image) {

        ByteBuffer data = attachHeader(DataType.IMG, toByteBuffer(image));

        NetIO.send(data);
    }

    public static void send(String message) {

        ByteBuffer data;
        if (message.charAt(0) == '/') {
            data = attachHeader(DataType.CMD, ByteBuffer.wrap(message.getBytes()));
        }
        else {
            data = attachHeader(DataType.MSG, ByteBuffer.wrap(message.getBytes()));
        }

        NetIO.send(data);
    }

    private static ByteBuffer attachHeader(DataType dataType, ByteBuffer buffer) {

        int dataSize = buffer.limit();
        ByteBuffer data = ByteBuffer.allocate(dataSize + 6);

        data.putChar(dataType.type);
        data.putInt(dataSize);
        data.put(buffer);
        data.flip();
        return data;
    }

    private static ByteBuffer toByteBuffer(BufferedImage image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(bos.toByteArray());
    }

    /**
     * Handle received data according to the type of the data.
     *
     * @param type Type of the received data.
     * @param srcID ID of the received data, only used in {@link com.thunder_cut.graphics.ui.frame.participants.ParticipantsFrame}.
     * @param data Received data.
     */
    public static void handleData(char type, int srcID, ByteBuffer data) {

        if (type == DataType.IMG.type) {
            drawImage.accept(srcID, data.array());
        }
        else {
            receiveMessage.accept(srcID, data.array());
        }
    }

    public static void addDrawImage(BiConsumer<Integer, byte[]> drawImage) {
        DataHandler.drawImage = drawImage;
    }

    public static void addReceiveMessage(BiConsumer<Integer, byte[]> receiveMessage) {
        DataHandler.receiveMessage = receiveMessage;
    }
}
