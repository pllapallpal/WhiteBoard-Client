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

    public final ByteBuffer sentData;

    public SentData(ByteBuffer data, DataType dataType) {

        data.flip();

        int dataSize = data.limit();
        sentData = ByteBuffer.allocate(dataSize + 6);
        sentData.putChar(dataType.type);
        sentData.putInt(dataSize);
        sentData.put(data);
    }
}
