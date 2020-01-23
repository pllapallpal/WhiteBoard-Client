/*
 * Brush.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.feature;

import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

import java.awt.*;

public class Brush implements DrawingFeature {
    private int prevXPos;
    private int prevYPos;
    private int currentX;
    private int currentY;

    @Override
    public void pressed(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        prevXPos = xPos;
        prevYPos = yPos;
        canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * yPos + xPos, color);
    }

    @Override
    public void dragged(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        boolean isPlusX = (prevXPos < xPos);
        boolean isPlusY = (prevYPos < yPos);
        currentX = prevXPos;
        currentY = prevYPos;
        int deltaX = Math.abs(xPos - prevXPos);
        int deltaY = Math.abs(yPos - prevYPos);
        boolean isMaxDeltaX = deltaX > deltaY;
        boolean isCurrentOverCanvas = setIsCurrentOverCanvas(canvasPixelInfo.getWidth(), canvasPixelInfo.getHeight());

        if((deltaX == 0) || (deltaY == 0)) {
            while((currentX != xPos) || (currentY != yPos)) {
                if(!isCurrentOverCanvas) {
                    canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * currentY + currentX, color);
                }

                controlPosition(isMaxDeltaX, isMaxDeltaX ? isPlusX : isPlusY);
                isCurrentOverCanvas = setIsCurrentOverCanvas(canvasPixelInfo.getWidth(), canvasPixelInfo.getHeight());
            }

            return;
        }

        int ratio = Math.max(deltaX, deltaY) / Math.min(deltaX, deltaY);
        int count = 0;

        while((currentX != xPos) && (currentY != yPos)) {
            if(!isCurrentOverCanvas) {
                canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * currentY + currentX, color);
            }

            if((count >= ratio)) {
                controlPosition(!isMaxDeltaX, isMaxDeltaX ? isPlusY : isPlusX);

                count = 0;
                deltaX = Math.abs(xPos - currentX);
                deltaY = Math.abs(yPos - currentY);
                ratio =  Math.max(deltaX, deltaY) / Math.min(deltaX, deltaY);
            }
            else {
                controlPosition(isMaxDeltaX, isMaxDeltaX ? isPlusX : isPlusY);

                count++;
            }

            isCurrentOverCanvas = setIsCurrentOverCanvas(canvasPixelInfo.getWidth(), canvasPixelInfo.getHeight());
        }

        prevXPos = xPos;
        prevYPos = yPos;
    }

    @Override
    public void released(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {

    }

    private void controlPosition(boolean isMaxDeltaX, boolean isPlus) {
        if (isMaxDeltaX) {
            currentX += (int) Math.pow(-1, isPlus ? 0 : 1);
            return;
        }
            currentY += (int) Math.pow(-1, isPlus ? 0 : 1);
    }

    private boolean setIsCurrentOverCanvas(int width, int height) {
        return ((currentX < 0) || (currentX >= width) || (currentY < 0) || (currentY >= height));
    }
}
