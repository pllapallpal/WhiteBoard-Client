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
import java.util.EnumMap;
import java.util.function.Consumer;

public class ToolPanel {
    private JPanel toolPanel;

    private Consumer<DrawingMode> drawHandler;
    private Consumer<RestoreMode> restoreHandler;
    private Runnable restoreDrawer;

    private EnumMap<DrawingMode,JButton> buttons;

    private JButton undo;
    private JButton redo;

    public ToolPanel(){
        toolPanel = new JPanel();

        buttons = new EnumMap<>(DrawingMode.class);

        undo = new JButton("Undo");
        redo = new JButton("Redo");

        for(DrawingMode mode : DrawingMode.values()){
            JButton button = new JButton(mode.DISPLAY_NAME);
            button.addActionListener(e -> drawHandler.accept(mode));
            buttons.put(mode,button);
        }

        undo.addActionListener(e -> {
            restoreHandler.accept(RestoreMode.UNDO);
            restoreDrawer.run();
        });
        redo.addActionListener(e -> {
            restoreHandler.accept(RestoreMode.REDO);
            restoreDrawer.run();
        });

        toolPanel.setPreferredSize(new Dimension(1280, 180));
        toolPanel.setBackground(Color.LIGHT_GRAY);

        buttons.forEach((mode,button) -> toolPanel.add(button));

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