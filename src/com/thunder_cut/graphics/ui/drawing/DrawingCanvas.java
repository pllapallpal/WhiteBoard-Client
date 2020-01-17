package com.thunder_cut.graphics.ui.drawing;

import com.thunder_cut.graphics.feature.MouseData;
import com.thunder_cut.graphics.feature.MouseStatus;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.Consumer;

public class DrawingCanvas {
    private Canvas canvas = new Canvas();
    private Consumer<MouseData> mouseHandler;

    public DrawingCanvas() {
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseHandler.accept(new MouseData(MouseStatus.PRESSED, e.getX(), e.getY()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseHandler.accept(new MouseData(MouseStatus.RELEASED, e.getX(), e.getY()));
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseHandler.accept(new MouseData(MouseStatus.DRAGGED, e.getX(), e.getY()));
            }
        });
    }

    public void addMouseHandler(Consumer<MouseData> mouseData) {
        this.mouseHandler = mouseData;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
