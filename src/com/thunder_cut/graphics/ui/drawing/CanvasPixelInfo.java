/*
 * CanvasPixelInfo.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.ui.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class CanvasPixelInfo {
    private int[] pixels;
    private int width;
    private int height;

    public CanvasPixelInfo(int width, int height, Color backgroundColor) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = backgroundColor.getRGB();
        }
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        for(int i = 0; i < imagePixels.length; i++) {
            imagePixels[i] = pixels[i];
        }

        return image;
    }

    public void setPixel(int index, Color color) {
        pixels[index] = color.getRGB();
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }
}
