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
public class DataUnwrapper {

    public final ByteBuffer rawData;

    public final char dataType;
    public final int srcID;
    public final int dstID;
    public final int dataSize;
    public final ByteBuffer data;

    public DataUnwrapper(ByteBuffer data) {

        data.flip();

        System.out.println(data.toString());

        rawData = data;
        dataType = data.getChar();
        srcID = data.getInt();
        dstID = data.getInt();
        dataSize = data.getInt();
        byte[] realData = new byte[data.limit() - 14];
        data.get(realData);
        this.data = ByteBuffer.wrap(realData);

        this.data.flip();
    }

    // TODO: 수신된 이미지 ID랑 데이터 구분해서 UI에 알려주기
    //       몇번 호스트꺼 이미지인지 && 이미지 부분만 바이트로 바꿔서 알려주기
    //  일단 된다고 가정하고 다음 작업 하기
}
