/*
 * LeftPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class DrawingPanel {
    private JPanel drawingPanel = new JPanel();
    private ToolPanel toolPanel = new ToolPanel();
    private Canvas canvas = new Canvas();

    public DrawingPanel(){
        drawingPanel.setLayout(new BorderLayout(20, 20));
        drawingPanel.setBackground(Color.GRAY);
        drawingPanel.add(toolPanel.getToolPanel(), BorderLayout.NORTH);
        drawingPanel.add(canvas, BorderLayout.CENTER);
        drawingPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        drawingPanel.setMinimumSize(new Dimension(1100, 900));

        canvas.setBackground(Color.LIGHT_GRAY);
    }

    public JPanel getDrawingPanel(){
        return drawingPanel;
    }
}
