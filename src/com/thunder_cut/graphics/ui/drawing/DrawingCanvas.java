/*
 * DrawingCanvas.java
 * Author : susemeeee
 * Created Date : 2020-01-17
 */
package com.thunder_cut.graphics.ui.drawing;

import com.thunder_cut.graphics.controller.MouseData;
import com.thunder_cut.graphics.controller.MouseStatus;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

public class DrawingCanvas {
    private Canvas canvas;
    private CanvasPixelInfo canvasPixelInfo;
    private BiConsumer<MouseData, CanvasPixelInfo> mouseHandler;

    public DrawingCanvas() {
        canvas = new Canvas();

        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseHandler.accept(new MouseData(MouseStatus.PRESSED, e.getX(), e.getY()), canvasPixelInfo);
                drawCanvas();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseHandler.accept(new MouseData(MouseStatus.RELEASED, e.getX(), e.getY()), canvasPixelInfo);
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseHandler.accept(new MouseData(MouseStatus.DRAGGED, e.getX(), e.getY()), canvasPixelInfo);
                drawCanvas();
            }
        });
        canvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                //Here you can check the changing size
            }
        });
    }

    public void addMouseHandler(BiConsumer<MouseData, CanvasPixelInfo> mouseHandler) {
        this.mouseHandler = mouseHandler;
    }

    public void createPixelInfo() {
        canvasPixelInfo = new CanvasPixelInfo(canvas.getWidth(), canvas.getHeight(), Color.WHITE);
    }

    public void drawCanvas() {
        BufferStrategy canvasBuffer = canvas.getBufferStrategy();

        if (canvasBuffer == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvasPixelInfo.toBufferedImage(image);

        Graphics2D g = (Graphics2D) canvasBuffer.getDrawGraphics();
        g.drawImage(image, 0, 0, canvas);
        g.dispose();
        canvasBuffer.show();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
