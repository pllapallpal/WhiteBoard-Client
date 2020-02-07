/*
 * ConnectionModule.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-01
 */
package com.thunder_cut.netio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * ConnectionModule is a class that supervises net i/o.
 */
public class ConnectionModule {

    private static Socket socket;
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private ParticipantsImageReceiver receiver;
    private Thread receivingThread;

    /**
     * <p>This is the constructor of class ConnectionModule.</p>
     * <p>object ConnectionModule should be created in main()</p>
     */
    public ConnectionModule() {
        try {
            ConnectionModule.socket = new Socket("whiteboard.sysbot32.com", 3001);
            ConnectionModule.inputStream = socket.getInputStream();
            ConnectionModule.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        receiver = new ParticipantsImageReceiver(inputStream);
        receivingThread = new Thread(receiver);
        receivingThread.start();
    }

    /**
     * <p>This is a static method that is called at
     * <ul>
     *  <li>mouseReleased() method, which is in constructor of DrawingCanvas class</li>
     *  <li>undo/redo method</li>
     * </ul></p>
     * @param data is data ready to be sent.
     */
    public static void send(byte[] data) {
        try {
            outputStream.write(data);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * temporary code (process and send image directly)
     * @param image
     */
    public static void send(BufferedImage image) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] data = byteArrayOutputStream.toByteArray();
            outputStream.write(data);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
