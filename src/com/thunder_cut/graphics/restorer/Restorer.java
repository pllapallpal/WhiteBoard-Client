/*
 * Restorer.java
 * Author : Cwhist
 * Created Date : 2020-02-01
 */
package com.thunder_cut.graphics.restorer;

import com.thunder_cut.graphics.controller.ChangedPixelUnitData;
import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

import java.awt.*;

public class Restorer {

    public void undo(WorkDataList workDataList, CanvasPixelInfo canvasPixelInfo) {
        if(workDataList.getPresentIndex() <= -1) {
            return;
        }

        WorkUnitData workUnitData = workDataList.getWorkUnitData();

        for (int i = 0; i < workUnitData.getSize(); i++) {
            ChangedPixelUnitData pixelUnitData = workUnitData.getPixelUnitData(i);
            canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * pixelUnitData.yPos + pixelUnitData.xPos, new Color(pixelUnitData.prevColor));
        }

        workDataList.setPresentIndex(-1);
    }

    public void redo(WorkDataList workDataList, CanvasPixelInfo canvasPixelInfo) {
        workDataList.setPresentIndex(1);

        if(!workDataList.isRearWorkExist()) {
            workDataList.setPresentIndex(-1);
            return;
        }
        WorkUnitData workUnitData = workDataList.getWorkUnitData();

        for(int i=0; i<workUnitData.getSize(); i++) {
            ChangedPixelUnitData pixelUnitData = workUnitData.getPixelUnitData(i);
            canvasPixelInfo.setPixel(canvasPixelInfo.getWidth() * pixelUnitData.yPos + pixelUnitData.xPos, new Color(pixelUnitData.changedColor));
        }

    }
}
