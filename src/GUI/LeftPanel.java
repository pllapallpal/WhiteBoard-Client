/*
 * LeftPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class LeftPanel {
    private JPanel leftPanel = new JPanel();
    private ToolPanel toolPanel = new ToolPanel();
    private MyCanvas canvas = new MyCanvas();

    public LeftPanel(){
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.BLACK);
        leftPanel.add(toolPanel.getToolPanel());
        leftPanel.add(canvas.getMyCanvas());

        leftPanel.setVisible(true);
    }

    public JPanel getLeftPanel(){
        return leftPanel;
    }
}
