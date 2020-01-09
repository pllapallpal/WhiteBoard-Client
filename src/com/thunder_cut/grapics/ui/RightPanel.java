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
    private JList<JPanel> panelList = new JList<>();
    private JScrollPane scrollPane = new JScrollPane(panelList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    public RightPanel(){
        rightPanel.setBackground(Color.GRAY);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        scrollPane.setPreferredSize(new Dimension(250,800));

        rightPanel.add(scrollPane);
    }

    private JPanel makePanel(){
        JPanel newPanel = new JPanel();
        newPanel.setBackground(Color.LIGHT_GRAY);
        newPanel.setPreferredSize(new Dimension(250, 250));
        newPanel.setVisible(true);

        return newPanel;
    }

    public JPanel getRightPanel(){
        return rightPanel;
    }
}
