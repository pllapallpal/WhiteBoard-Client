/*
 * DataUnwrapper.java
 * Author : Hyeokwoo Kwon
 * Created Date : 2020-02-07
 */
package com.thunder_cut.netio;

import java.nio.ByteBuffer;

/**
 * Includes information of data that has come from server
 * <p>
 * (rawData, dataType, srcID, dstID, dataSize, actual data)
 */
public class DataUnwrapper {

    public final char dataType;
    public final int srcID;
    public final int dstID;
    public final int dataSize;
    public final ByteBuffer data;

    public DataUnwrapper(ByteBuffer data) {

        data.flip();

        dataType = data.getChar();
        srcID = data.getInt();
        dstID = data.getInt();
        dataSize = data.getInt();
        byte[] realData = new byte[data.limit() - 14];
        data.get(realData);
        this.data = ByteBuffer.wrap(realData);

        this.data.flip();
    }
}
