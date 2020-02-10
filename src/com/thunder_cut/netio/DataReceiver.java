/*
 * ParticipantsImageReceiver.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * <p>A class for receiving other participants' image</p>
 * It has infinite loop, and is run in constructor of ConnectionModule
 */
public class ParticipantsImageReceiver implements Runnable {

    private InputStream inputStream;
    private BufferedImage image;

    public ParticipantsImageReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] data = new byte[8192];

                inputStream.read(data);
                ReceivedData receivedData = new ReceivedData(ByteBuffer.wrap(data));
                ByteBuffer rawData = ByteBuffer.allocate(receivedData.dataSize + 14);
                rawData.put(data);

                int dataSize = receivedData.dataSize;
                while (dataSize > 0) {
                    inputStream.read(data);
                    rawData.put(data);
                    dataSize = dataSize - 8192;
                }

                receivedData = new ReceivedData(rawData);

                byte[] completedData = byteArrayOutputStream.toByteArray();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(completedData);
                image = ImageIO.read(byteArrayInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
