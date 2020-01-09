/*
 * MyCanvas.java
 * Author : 김태건
 * Created Date : 2020-01-08
 */
package GUI;

import java.awt.*;

public class MyCanvas {
    private Canvas myCanvas = new Canvas();
    public MyCanvas(){
        myCanvas.setBackground(Color.DARK_GRAY);
    }

    public Canvas getMyCanvas(){
        return myCanvas;
    }
}