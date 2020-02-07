/*
 * ToolPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.graphics.ui.drawing;

import com.thunder_cut.graphics.controller.DrawingMode;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ToolPanel {
    private JPanel toolPanel;
    private JButton brush;
    private JButton eraser;
    private JButton colorSelect;
    private Consumer<DrawingMode> drawHandler;

    public ToolPanel(){
        toolPanel = new JPanel();
        brush = new JButton("브러쉬");
        eraser = new JButton("지우개");
        colorSelect = new JButton("색상선택");

        toolPanel.setPreferredSize(new Dimension(1280, 180));
        toolPanel.setBackground(Color.LIGHT_GRAY);

        brush.addActionListener(e -> {
            drawHandler.accept(DrawingMode.BRUSH);
        });
        eraser.addActionListener(e -> {
            drawHandler.accept(DrawingMode.ERASER);
        });
        colorSelect.addActionListener(e -> {
            drawHandler.accept(DrawingMode.COLOR_CHOOSER);
        });

        toolPanel.add(brush);
        toolPanel.add(eraser);
        toolPanel.add(colorSelect);
    }

    public void addDrawModeHandler(Consumer<DrawingMode> handler) {
        drawHandler = handler;
    }

    public JPanel getToolPanel(){
        return toolPanel;
    }
}