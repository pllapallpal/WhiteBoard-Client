/*
 * DrawingModeHandler.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.feature;

import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class DrawingModeHandler {
    private DrawingMode selectedDrawingMode = DrawingMode.BRUSH;
    private EnumMap<DrawingMode, DrawingFeature> drawingFeatures;
    private Color color = new Color(Color.BLACK.getRGB());

    public DrawingModeHandler() {
        drawingFeatures = new EnumMap<>(DrawingMode.class);
        drawingFeatures.put(DrawingMode.BRUSH, new Brush());
    }

    public void drawingModeChanged(DrawingMode mode) {
        if(mode == DrawingMode.COLOR_CHOOSER) {
            color = JColorChooser.showDialog(null, "Color", Color.GRAY);
        }
        else {
            selectedDrawingMode = mode;
        }
    }

    public void handleMouseEvent(MouseData mouseData, CanvasPixelInfo canvasPixelInfo) {
        drawingFeatures.get(selectedDrawingMode).process(mouseData, canvasPixelInfo, color);
    }
}
