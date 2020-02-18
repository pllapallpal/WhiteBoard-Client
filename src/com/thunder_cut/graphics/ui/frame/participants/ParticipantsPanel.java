/*
 * ParticipantsPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.graphics.ui.frame.participants;

import com.thunder_cut.graphics.ui.theme.Theme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ParticipantsPanel {
    private final int DEFAULT_GAP = 10;
    private JPanel participantsPanel;

    private List<JPanel> participants;
    private Map<Integer,BufferedImage> lastImages;

    public ParticipantsPanel(){

        participants = new ArrayList<>();
        lastImages = new HashMap<>();

        participantsPanel = new JPanel();
        participantsPanel.setBackground(Theme.CURRENT.background);
        participantsPanel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_GAP, DEFAULT_GAP, DEFAULT_GAP, DEFAULT_GAP));
        participantsPanel.setLayout(new BoxLayout(participantsPanel, BoxLayout.Y_AXIS));

        participantsPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                redrawParticipantPanel();
            }
        });

        makePanel();
    }

    private void makePanel(){
        JPanel newPanel = new JPanel();
        newPanel.setBackground(Theme.CURRENT.primary);
        newPanel.setBorder(BorderFactory.createLineBorder(Theme.CURRENT.background, DEFAULT_GAP));

        newPanel.setMaximumSize(new Dimension(480, 270));
        newPanel.setPreferredSize(new Dimension(480, 270));

        participants.add(newPanel);

        participantsPanel.add(newPanel);
        participantsPanel.revalidate();
        participantsPanel.repaint();

    }

    public void drawImage(int srcID, byte[] imageData){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
        while(srcID>=participants.size()){
            makePanel();
        }
        try {
            BufferedImage bi =  ImageIO.read(byteArrayInputStream);
            if(Objects.nonNull(lastImages.get(srcID))){
                lastImages.replace(srcID,bi);
            }
            else{
                lastImages.put(srcID,bi);
            }

            JPanel target = participants.get(srcID);
            target.getGraphics()
                    .drawImage(bi,5,5,target.getWidth()-DEFAULT_GAP,target.getHeight()-DEFAULT_GAP,null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawImage(int srcID, BufferedImage image){

        JPanel target = participants.get(srcID);
        target.getGraphics()
                .drawImage(image,5,5,target.getWidth()-DEFAULT_GAP,target.getHeight()-DEFAULT_GAP,null);
    }

    public void redrawParticipantPanel(){
        for(int i = 0; i<participants.size(); ++i)
            drawImage(i,lastImages.get(i));
    }


    public JPanel getParticipantsPanel(){
        return participantsPanel;
    }
}
