/*
 * RightPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class RightPanel {
    private JPanel rightPanel = new JPanel();
    private static int ID = -1;

    public RightPanel(){
        rightPanel.setBackground(Color.GRAY);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        //FirstPanel is your canvas
        insertPanel();
    }

    private JPanel makePanel(){
        JPanel newPanel = new JPanel();
        newPanel.setBackground(Color.LIGHT_GRAY);
        newPanel.setPreferredSize(new Dimension(250, 250));
        newPanel.setMaximumSize(new Dimension(1000, 250));
        newPanel.setVisible(true);
        ID += 2;

        return newPanel;
    }

    private void insertPanel(){
        rightPanel.add(makePanel());
        rightPanel.add(Box.createRigidArea(new Dimension(10,15)));
        rightPanel.updateUI();
    }

    private void deletePanel(){
        rightPanel.remove(ID--);
        rightPanel.remove(ID--);
        rightPanel.updateUI();
    }

    public JPanel getRightPanel(){
        return rightPanel;
    }
}
