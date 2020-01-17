/*
 * DrawingModeHandler.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.feature;

import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

import java.awt.*;

public class DrawingModeHandler {
    private DrawingMode selectedDrawingMode = DrawingMode.BRUSH;
    private int prevXPos;
    private int prevYPos;
    private Color color = new Color(Color.BLACK.getRGB());


    public void toolChanged(DrawingMode mode) {
        selectedDrawingMode = mode;
    }

    public void handleMouseEvent(MouseData mouseData, CanvasPixelInfo canvasPixelInfo) {

    }


}
