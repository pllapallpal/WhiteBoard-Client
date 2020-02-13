/*
 * AreaSelector.java
 * Author : Cwhist
 * Created Date : 2020-01-21
 */
package com.thunder_cut.graphics.feature;

import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

import java.awt.*;

public class AreaSelector implements DrawingFeature {
    private int startXPos;
    private int startYPos;
    private int endXPos;
    private int endYPos;

    @Override
    public void pressed(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        startXPos = xPos;
        startYPos = yPos;
    }

    @Override
    public void dragged(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        if(!isOverCanvas(xPos, yPos, canvasPixelInfo.getWidth(), canvasPixelInfo.getHeight())) {
            endXPos = xPos;
            endYPos = yPos;
        }
        canvasPixelInfo.initEffectPixels();
        makeBorderEffect(xPos,yPos,canvasPixelInfo);
    }

    @Override
    public void released(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        if(!isOverCanvas(xPos, yPos, canvasPixelInfo.getWidth(), canvasPixelInfo.getHeight())) {
            endXPos = xPos;
            endYPos = yPos;
        }
        canvasPixelInfo.initEffectPixels();
        makeBorderEffect(xPos,yPos,canvasPixelInfo);
    }

    @Override
    public void moved(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {

    }

    public void makeBorderEffect(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo) {
        int lowX;
        int lowY;
        int highX;
        int highY;

        if(startXPos > endXPos) {
            lowX = endXPos;
            highX = startXPos;
        }
        else {
            lowX = startXPos;
            highX = endXPos;
        }
        if(startYPos > endYPos) {
            lowY = endYPos;
            highY = startYPos;
        }
        else {
            lowY = startYPos;
            highY = endYPos;
        }

        for(int i = lowX; i < highX; i++) {
            if(i % 12 > 6) {
                canvasPixelInfo.setEffectPixel(canvasPixelInfo.getWidth() * lowY + i, Color.BLACK);
                canvasPixelInfo.setEffectPixel(canvasPixelInfo.getWidth() * highY + i, Color.BLACK);
            }
        }
        for(int i = lowY; i < highY; i++) {
            if(i % 12 > 6) {
                canvasPixelInfo.setEffectPixel(canvasPixelInfo.getWidth() * i + lowX, Color.BLACK);
                canvasPixelInfo.setEffectPixel(canvasPixelInfo.getWidth() * i + highX, Color.BLACK);
            }
        }

    }
}
