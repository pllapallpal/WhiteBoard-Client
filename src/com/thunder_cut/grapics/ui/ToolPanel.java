/*
 * ToolPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class ToolPanel {
    private JPanel toolPanel = new JPanel();
    private JButton brush = new JButton("브러쉬");
    private JButton eraser = new JButton("지우개");
    private JButton colorSelect = new JButton("색상선택");

    public ToolPanel(){
        //toolPanel.setLayout(new GridLayout(1, 4, 10, 10));
        toolPanel.setPreferredSize(new Dimension(1280, 180));
        toolPanel.setBackground(Color.LIGHT_GRAY);

        toolPanel.add(brush);
        toolPanel.add(eraser);
        toolPanel.add(colorSelect);

        colorSelect.addActionListener(e->{
            selectColor();
        });
    }

    public JPanel getToolPanel(){
        return toolPanel;
    }

    public Color selectColor(){
        JColorChooser chooser = new JColorChooser();
        Color selectedColor = chooser.showDialog(null, "Color", Color.GRAY);

        return selectedColor;
    }
}