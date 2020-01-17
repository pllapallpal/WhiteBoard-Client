package com.thunder_cut.graphics.feature;

public class MouseData {
    public final MouseStatus status;
    public final int xPos;
    public final int yPos;

    public MouseData(MouseStatus status, int xPos, int yPos) {
        this.status = status;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
