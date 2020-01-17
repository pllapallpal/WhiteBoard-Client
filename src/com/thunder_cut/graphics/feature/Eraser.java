/*
 * Eraser.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.feature;

import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

import java.awt.*;

public class Eraser extends Brush {
    @Override
    public void pressed(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        super.pressed(xPos, yPos, canvasPixelInfo, Color.WHITE);
    }

    @Override
    public void dragged(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        super.dragged(xPos, yPos, canvasPixelInfo, Color.WHITE);
    }

    @Override
    public void released(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        super.released(xPos, yPos, canvasPixelInfo, Color.WHITE);
    }
}
