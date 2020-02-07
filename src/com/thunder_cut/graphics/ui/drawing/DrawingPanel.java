/*
 * LeftPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.graphics.ui.drawing;

import com.thunder_cut.graphics.controller.DrawingModeHandler;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel {
    private JPanel drawingPanel;
    private ToolPanel toolPanel;
    private DrawingCanvas drawingCanvas;
    private DrawingModeHandler drawingModeHandler;

    public DrawingPanel(){

        drawingPanel = new JPanel();
        toolPanel = new ToolPanel();
        drawingCanvas = new DrawingCanvas();
        drawingModeHandler = new DrawingModeHandler();


        drawingPanel.setLayout(new BorderLayout(20, 20));
        drawingPanel.setBackground(Color.GRAY);
        drawingPanel.add(toolPanel.getToolPanel(), BorderLayout.NORTH);
        drawingPanel.add(drawingCanvas.getCanvas(), BorderLayout.CENTER);

        drawingPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        drawingPanel.setMinimumSize(new Dimension(1100, 900));
        drawingCanvas.addMouseHandler(drawingModeHandler::handleMouseEvent);

        toolPanel.addDrawModeHandler(drawingModeHandler::drawingModeChanged);
    }

    public JPanel getDrawingPanel(){
        return drawingPanel;
    }

    public DrawingCanvas getDrawingCanvas() {
        return drawingCanvas;
    }
}
