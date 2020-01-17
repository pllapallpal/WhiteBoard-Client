/*
 * RightPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class ParticipantsPanel {
    private JPanel participantsPanel = new JPanel();
    private static int ID = -1;

    public ParticipantsPanel(){
        participantsPanel.setBackground(Color.GRAY);
        participantsPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        participantsPanel.setLayout(new BoxLayout(participantsPanel, BoxLayout.Y_AXIS));

        //FirstPanel is your canvas
        insertPanel(makePanel());
    }

    private JPanel makePanel(){
        JPanel newPanel = new JPanel();
        newPanel.setBackground(Color.LIGHT_GRAY);
        newPanel.setPreferredSize(new Dimension(250, 250));
        newPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 15));

        return newPanel;
    }

    private void insertPanel(JPanel jp){
        participantsPanel.add(jp);
        ID += 1;
        participantsPanel.revalidate();
        participantsPanel.repaint();
    }

    private void deletePanel(){
        participantsPanel.remove(ID--);
        participantsPanel.revalidate();
        participantsPanel.repaint();
    }

    public JPanel getParticipantsPanel(){
        return participantsPanel;
    }
}
