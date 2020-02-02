/*
 * LeftPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.graphics.ui.drawing;

import com.thunder_cut.graphics.controller.DrawingModeHandler;
import com.thunder_cut.graphics.controller.RestoreHandler;
import com.thunder_cut.graphics.controller.WorkDataRecorder;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel {
    private JPanel drawingPanel = new JPanel();
    private ToolPanel toolPanel = new ToolPanel();
    private DrawingCanvas drawingCanvas = new DrawingCanvas();
    private DrawingModeHandler drawingModeHandler = new DrawingModeHandler();
    private WorkDataRecorder workDataRecorder = new WorkDataRecorder();
    private RestoreHandler restoreHandler = new RestoreHandler();
    private Runnable restoreDrawer = drawingCanvas::drawCanvas;

    public DrawingPanel() {
        drawingPanel.setLayout(new BorderLayout(20, 20));
        drawingPanel.setBackground(Color.GRAY);
        drawingPanel.add(toolPanel.getToolPanel(), BorderLayout.NORTH);
        drawingPanel.add(drawingCanvas.getCanvas(), BorderLayout.CENTER);
        drawingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        drawingPanel.setMinimumSize(new Dimension(1100, 900));
        drawingCanvas.addMouseHandler(drawingModeHandler::handleMouseEvent);
        drawingCanvas.addWorkDataRecorder(workDataRecorder::handleRecordWorkData);
        toolPanel.addDrawModeHandler(drawingModeHandler::drawingModeChanged);
        toolPanel.addRestoreHandler(restoreHandler::handleRestoreEvent);
        toolPanel.setRestoreDrawer(restoreDrawer);
        restoreHandler.setWorkDataRecorder(workDataRecorder);
    }

    public JPanel getDrawingPanel() {
        return drawingPanel;
    }

    public DrawingCanvas getDrawingCanvas() {
        return drawingCanvas;
    }

    public void setRecorderPixelInfo(CanvasPixelInfo canvasPixelInfo) {
        workDataRecorder.setCanvasPixelInfo(canvasPixelInfo);
    }

}
