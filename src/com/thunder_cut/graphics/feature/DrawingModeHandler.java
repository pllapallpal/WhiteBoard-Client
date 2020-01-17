/*
 * DrawingModeHandler.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.feature;

public class DrawingModeHandler {
    private DrawingMode selectedDrawingMode = DrawingMode.BRUSH;


    public void toolChanged(DrawingMode mode) {
        selectedDrawingMode = mode;
    }

    public void handleMouseEvent(MouseData mouseData) {

    }
}
