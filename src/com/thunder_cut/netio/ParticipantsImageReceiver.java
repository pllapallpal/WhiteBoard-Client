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
                byte[] data = new byte[16384];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                // 메소드로 따로 분리
                // 헤더에 있는 사이즈 이용하는 거로 다시 짜야 함
                int readCount = 0;
                while (true) {
                    for (int i = 0; i < 16384; ++i) {
                        data[i] = 0;
                    }
                    readCount = readCount + inputStream.read(data);
                    byteArrayOutputStream.write(data);
                    if (readCount % 16384 != 0) {
                        break;
                    }
                }

                data = byteArrayOutputStream.toByteArray();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
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
