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
    private JPanel toolPanel = new JPanel();
    private JButton brush = new JButton("브러쉬");
    private JButton eraser = new JButton("지우개");
    private JButton colorSelect = new JButton("색상선택");
    private JButton areaSelector = new JButton("영역선택");
    private Consumer<DrawingMode> drawHandler;

    public ToolPanel(){
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
        areaSelector.addActionListener(e -> {
            drawHandler.accept(DrawingMode.AREA_SELECTOR);
        });

        toolPanel.add(brush);
        toolPanel.add(eraser);
        toolPanel.add(colorSelect);
        toolPanel.add(areaSelector);
    }

    public void addDrawModeHandler(Consumer<DrawingMode> handler) {
        drawHandler = handler;
    }

    public JPanel getToolPanel(){
        return toolPanel;
    }
}