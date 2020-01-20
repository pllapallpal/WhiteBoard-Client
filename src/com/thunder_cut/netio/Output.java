/*
 * Output.java
 * Author : pllapallpal
 * Created Date : 2020-01-20
 */
package com.thunder_cut.netio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Output {

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BufferedImage image;                //TODO: get image from canvas
    private ByteArrayOutputStream byteOut;
    private ByteArrayInputStream byteIn;

    public Output(String address, int port) {
        try {
            socket = new Socket(address, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();

            byteOut = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteOut);

            byte[] imageSize = ByteBuffer.allocate(4).putInt(byteOut.size()).array();
            byte[] imageData = byteOut.toByteArray();

            outputStream.write(imageSize);
            outputStream.write(imageData);
            outputStream.flush();

        } catch (IOException e) {

        }
    }

}
