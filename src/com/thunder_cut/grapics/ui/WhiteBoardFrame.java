/*
 * WhiteBoardFrame.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package com.thunder_cut.grapics.ui;

import javax.swing.*;
import java.awt.*;

public class WhiteBoardFrame {
    private JFrame mainFrame = new JFrame("화이트 보드");
    private JMenuBar menuBar = makeMenu();
    private JSplitPane split = new JSplitPane();
    private CanvasPanel canvasPanel = new CanvasPanel();
    private OthersPanel othersPanel = new OthersPanel();
    private JScrollPane scrollPane = new JScrollPane(othersPanel.getOthersPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private static final Dimension frameSize = new Dimension(1600, 900);
    private static final int dividerX = (int)(frameSize.width*0.8);

    public WhiteBoardFrame(){
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(frameSize);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setJMenuBar(menuBar);

        split.setDividerLocation(dividerX);
        split.setLeftComponent(canvasPanel.getCanvasPanel());
        split.setRightComponent(scrollPane);

        mainFrame.add(split);

        mainFrame.setVisible(true);
    }

    private JMenuBar makeMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("파일");
        JMenuItem makeNewFileMenuItem = new JMenuItem("새로 만들기");
        JMenuItem openFileMenuItem = new JMenuItem("열기");
        JMenuItem saveFileMenuItem = new JMenuItem("저장");
        JMenuItem saveOtherNameMenuItem = new JMenuItem("다른 이름으로 저장");
        JMenuItem exitMenuItem = new JMenuItem("끝내기");
        exitMenuItem.addActionListener(e->{
            if(JOptionPane.showConfirmDialog(mainFrame, "종료하시겠습니까?", mainFrame.getTitle(), 0) == 0)
                System.exit(0);
        });

        fileMenu.add(makeNewFileMenuItem);
        fileMenu.add(openFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.add(saveOtherNameMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("편집");
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem redoMenuItem = new JMenuItem("Redo");

        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        return menuBar;
    }
}
