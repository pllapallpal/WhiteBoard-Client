/*
 * RightPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class OthersPanel {
    private JPanel othersPanel = new JPanel();
    private static int ID = -1;

    public OthersPanel(){
        othersPanel.setBackground(Color.GRAY);
        othersPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        othersPanel.setLayout(new BoxLayout(othersPanel, BoxLayout.Y_AXIS));

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
        othersPanel.add(makePanel());
        othersPanel.add(Box.createRigidArea(new Dimension(10,15)));
        othersPanel.updateUI();
    }

    private void deletePanel(){
        othersPanel.remove(ID--);
        othersPanel.remove(ID--);
        othersPanel.updateUI();
    }

    public JPanel getOthersPanel(){
        return othersPanel;
    }
}
