package com.thunder_cut.netio;

public enum DataType {

    IMG('I'),
    MSG('M'),
    CMD('C');

    public final char type;

    DataType(char type) {
        this.type = type;
    }
}
