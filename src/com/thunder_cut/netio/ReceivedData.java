/*
 * ReceivedData.java
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
public class ReceivedData {

    public final ByteBuffer rawData;

    public final DataType dataType;
    public final int srcID;
    public final int dstID;
    public final int dataSize;
    public final ByteBuffer data;

    public ReceivedData(ByteBuffer data) {

        data.flip();

        rawData = data;
        dataType = DataType.valueOf(Character.toString(data.getChar()));
        srcID = data.getInt();
        dstID = data.getInt();
        dataSize = data.getInt();
        byte[] realData = new byte[data.limit() - 14];
        data.get(realData);
        this.data = data;
    }

    // TODO:  * 호스트 번호로 이미지 따로 받아오기
    //        * 이미지 수신하면 ID랑 데이터 구분해서 UI에 알려주기
    //          몇번 호스트꺼 이미지인지 && 이미지 부분만 바이트로 바꿔서 알려주기
}
