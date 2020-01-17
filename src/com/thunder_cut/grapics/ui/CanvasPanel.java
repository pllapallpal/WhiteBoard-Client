/*
 * LeftPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class CanvasPanel {
    private JPanel canvasPanel = new JPanel();
    private ToolPanel toolPanel = new ToolPanel();
    private Canvas canvas = new Canvas();

    public CanvasPanel(){
        canvasPanel.setLayout(new BorderLayout(20, 20));
        canvasPanel.setBackground(Color.GRAY);
        canvasPanel.add(toolPanel.getToolPanel(), BorderLayout.NORTH);
        canvasPanel.add(canvas, BorderLayout.CENTER);
        canvasPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        canvasPanel.setMinimumSize(new Dimension(1100, 900));

        canvas.setBackground(Color.LIGHT_GRAY);
    }

    public JPanel getCanvasPanel(){
        return canvasPanel;
    }
}
