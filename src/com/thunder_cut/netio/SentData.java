/*
 * SentData.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-07
 */
package com.thunder_cut.netio;

import java.nio.ByteBuffer;

/**
 * Includes information of data that'll be sent to server
 * <p>
 * (rawData, dataType, dataSize, actual data)
 */
public class SentData {

    public final ByteBuffer rawData;

    public final DataType dataType;
    public final int dataSize;
    public final ByteBuffer data;

    public SentData(ByteBuffer data) {

        data.flip();
        rawData = data;
        dataType = DataType.valueOf(Character.toString(data.getChar()));
        dataSize = data.getInt();
        byte[] realData = new byte[data.limit() - 6];
        data.get(realData);
        this.data = data;
    }
}
