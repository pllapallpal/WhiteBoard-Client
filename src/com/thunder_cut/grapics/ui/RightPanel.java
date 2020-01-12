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
    private JButton insert = new JButton("넣기");
    private JButton delete = new JButton("지우기");
    private static int ID = 1;

    public RightPanel(){
        rightPanel.setBackground(Color.GRAY);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15,15,20,20));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(insert);
        rightPanel.add(delete);
        rightPanel.add(makePanel());

        insert.addActionListener(e->{
            rightPanel.add(makePanel());
            rightPanel.updateUI();
        });

        delete.addActionListener(e->{
            deletePanel();
            rightPanel.updateUI();
        });
    }

    private JPanel makePanel(){
        JPanel newPanel = new JPanel();
        newPanel.setBackground(Color.LIGHT_GRAY);
        newPanel.setPreferredSize(new Dimension(250, 250));
        newPanel.setVisible(true);
        ID++;

        return newPanel;
    }

    private void deletePanel(){
        rightPanel.remove(ID);
        ID--;
    }

    public JPanel getRightPanel(){
        return rightPanel;
    }
}
