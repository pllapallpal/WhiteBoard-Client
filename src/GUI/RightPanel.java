/*
 * RightPanel.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class RightPanel {
    private JPanel rightPanel = new JPanel();
    public RightPanel(){
        rightPanel.setBackground(Color.LIGHT_GRAY);
    }

    public JPanel getRightPanel(){
        return rightPanel;
    }
}
