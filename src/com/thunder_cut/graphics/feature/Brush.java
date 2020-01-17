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
        int currentX = prevXPos;
        int currentY = prevYPos;
        int deltaX = Math.abs(xPos - prevXPos);
        int deltaY = Math.abs(yPos - prevYPos);

        if(deltaX == 0) {
            while(currentY != yPos) {
                canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * currentY + currentX, color);

                if(isPlusY) {
                    currentY++;
                }
                else {
                    currentY--;
                }
            }

            return;
        }

        if(deltaY == 0) {
            while(currentX != xPos) {
                canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * currentY + currentX, color);

                if(isPlusX) {
                    currentX++;
                }
                else {
                    currentX--;
                }
            }

            return;
        }

        double ratio;
        int count = 0;

        if(deltaX > deltaY) {
            ratio = deltaX / deltaY;
        }
        else {
            ratio = deltaY / deltaX;
        }

        while((currentX != xPos) && (currentY != yPos)) {
            canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * currentY + currentX, color);

            if((count >= (int)ratio)) {
                if(deltaX > deltaY) {
                    if(isPlusY && (currentY != yPos)) {
                        currentY++;
                    }
                    else if(currentY != yPos) {
                        currentY--;
                    }
                }
                else {
                    if(isPlusX && (currentX != xPos)) {
                        currentX++;
                    }
                    else if(currentX != xPos) {
                        currentX--;
                    }
                }

                count = 0;
                deltaX = Math.abs(xPos - currentX);
                deltaY = Math.abs(yPos - currentY);
                if(deltaX > deltaY) {
                    ratio = deltaX / deltaY;
                }
                else {
                    ratio = deltaY / deltaX;
                }
            }
            else {
                if (deltaX > deltaY) {
                    if (isPlusX && (currentX != xPos)) {
                        currentX++;
                    }
                    else if (currentX != xPos) {
                        currentX--;
                    }
                }
                else {
                    if (isPlusY && (currentY != yPos)) {
                        currentY++;
                    }
                    else if (currentY != yPos) {
                        currentY--;
                    }
                }

                count++;
            }
        }

        prevXPos = xPos;
        prevYPos = yPos;
    }

    @Override
    public void released(int xPos, int yPos, CanvasPixelInfo canvasPixelInfo, Color color) {
        canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * yPos + xPos, color);
    }
}
