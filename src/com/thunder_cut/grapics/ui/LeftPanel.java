/*
 * LeftPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class LeftPanel {
    private JPanel leftPanel = new JPanel();
    private ToolPanel toolPanel = new ToolPanel();
    private Canvas canvas = new Canvas();

    public LeftPanel(){
        leftPanel.setLayout(new BorderLayout(20, 20));
        leftPanel.setBackground(Color.GRAY);
        leftPanel.add(toolPanel.getToolPanel(), BorderLayout.NORTH);
        leftPanel.add(canvas, BorderLayout.CENTER);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        leftPanel.setMinimumSize(new Dimension(1100, 900));

        canvas.setBackground(Color.LIGHT_GRAY);

        leftPanel.setVisible(true);
    }

    public JPanel getLeftPanel(){
        return leftPanel;
    }
}
