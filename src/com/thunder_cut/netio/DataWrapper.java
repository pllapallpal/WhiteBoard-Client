/*
 * DataWrapper.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-07
 */
package com.thunder_cut.netio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Includes information of data that'll be sent to server
 * <p>
 * (rawData, dataType, dataSize, actual data)
 */
public class DataWrapper {

    public final ByteBuffer wrappedData;

    public DataWrapper(BufferedImage image) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", bos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteBuffer data = ByteBuffer.wrap(bos.toByteArray());

        int dataSize = data.limit();
        wrappedData = ByteBuffer.allocate(dataSize + 6);
        wrappedData.putChar(DataType.IMG.type);
        wrappedData.putInt(dataSize);
        wrappedData.put(data);

        wrappedData.flip();
    }

    public DataWrapper(ByteBuffer data, DataType dataType) {

        int dataSize = data.limit();
        wrappedData = ByteBuffer.allocate(dataSize + 6);
        wrappedData.putChar(dataType.type);
        wrappedData.putInt(dataSize);
        wrappedData.put(data);

        wrappedData.flip();
    }
}
