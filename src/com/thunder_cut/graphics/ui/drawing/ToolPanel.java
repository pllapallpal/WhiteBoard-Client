/*
 * ToolPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.graphics.ui.drawing;

import com.thunder_cut.graphics.controller.DrawingMode;
import com.thunder_cut.graphics.restorer.RestoreMode;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ToolPanel {
    private JPanel toolPanel = new JPanel();
    private JButton brush = new JButton("브러쉬");
    private JButton eraser = new JButton("지우개");
    private JButton colorSelect = new JButton("색상선택");
    private JButton areaSelector = new JButton("영역선택");
    private JButton undo = new JButton("Undo");
    private JButton redo = new JButton("Redo");
    private Consumer<DrawingMode> drawHandler;
    private Consumer<RestoreMode> restoreHandler;
    private Runnable restoreDrawer;

    public ToolPanel() {
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
        undo.addActionListener(e -> {
            restoreHandler.accept(RestoreMode.UNDO);
            restoreDrawer.run();
        });
        redo.addActionListener(e -> {
            restoreHandler.accept(RestoreMode.REDO);
            restoreDrawer.run();
        });

        toolPanel.add(brush);
        toolPanel.add(eraser);
        toolPanel.add(colorSelect);
        toolPanel.add(areaSelector);
        toolPanel.add(undo);
        toolPanel.add(redo);
    }

    public void addDrawModeHandler(Consumer<DrawingMode> handler) {
        drawHandler = handler;
    }

    public void addRestoreHandler(Consumer<RestoreMode> handler) {
        restoreHandler = handler;
    }

    public void setRestoreDrawer(Runnable restoreDrawer) {
        this.restoreDrawer = restoreDrawer;
    }

    public JPanel getToolPanel() {
        return toolPanel;
    }
}