/*
 * ToolPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class ToolPanel {
    private JPanel toolPanel = new JPanel();
    public ToolPanel(){
        toolPanel.setLayout(new GridLayout(2, 4, 10, 10));
        toolPanel.setSize(new Dimension(1280, 200));
        toolPanel.setBackground(Color.GRAY);
        toolPanel.setVisible(true);
    }

    public JPanel getToolPanel(){
        return toolPanel;
    }
}