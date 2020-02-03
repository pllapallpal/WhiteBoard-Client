/*
 * DrawingModeHandler.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.controller;

import com.thunder_cut.graphics.feature.Brush;
import com.thunder_cut.graphics.feature.DrawingFeature;
import com.thunder_cut.graphics.feature.Eraser;
import com.thunder_cut.graphics.feature.AreaSelector;
import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class DrawingModeHandler {
    private DrawingMode selectedDrawingMode;
    private EnumMap<DrawingMode, DrawingFeature> drawingFeatures;
    private Color color;

    public DrawingModeHandler() {
        selectedDrawingMode = DrawingMode.BRUSH;
        color = new Color(Color.BLACK.getRGB());

        drawingFeatures = new EnumMap<>(DrawingMode.class);
        drawingFeatures.put(DrawingMode.BRUSH, new Brush());
        drawingFeatures.put(DrawingMode.ERASER, new Eraser());
        drawingFeatures.put(DrawingMode.AREA_SELECTOR, new AreaSelector());
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
