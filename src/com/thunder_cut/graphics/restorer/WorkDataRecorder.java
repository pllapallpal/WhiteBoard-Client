/*
 * WorkDataRecorder.java
 * Author : Cwhist
 * Created Date : 2020-02-01
 */
package com.thunder_cut.graphics.restorer;

import com.thunder_cut.graphics.controller.MouseStatus;
import com.thunder_cut.graphics.ui.drawing.CanvasPixelInfo;

public class WorkDataRecorder {
    private WorkDataList workDataList = new WorkDataList();
    private CanvasPixelInfo canvasPixelInfo;
    private int[] prevPixels;

    public void handleRecordWorkData(MouseStatus mouseStatus) {
        if(mouseStatus == MouseStatus.PRESSED) {
            prevPixels = new int[canvasPixelInfo.getWidth()*canvasPixelInfo.getHeight()];
            for(int i=0; i<canvasPixelInfo.getPixels().length; i++) {
                prevPixels[i] = canvasPixelInfo.getPixels()[i];
            }
        }
        else if(mouseStatus == MouseStatus.RELEASED) {
            WorkUnitData workUnitData = new WorkUnitData();
            boolean isChanged = false;
            for(int i=0; i<canvasPixelInfo.getPixels().length; i++) {
                if (prevPixels[i] != canvasPixelInfo.getPixels()[i]) {
                    workUnitData.addData(new ChangedPixelUnitData
                            (i%canvasPixelInfo.getWidth(),i/canvasPixelInfo.getWidth(), prevPixels[i], canvasPixelInfo.getPixels()[i]));
                    isChanged = true;
                }
            }
            if(isChanged) {
                workDataList.add(workUnitData);
            }
        }
    }

    public void setCanvasPixelInfo(CanvasPixelInfo canvasPixelInfo) {
        this.canvasPixelInfo = canvasPixelInfo;
    }

    public WorkDataList getWorkDataList() {
        return workDataList;
    }

    public CanvasPixelInfo getCanvasPixelInfo() {
        return canvasPixelInfo;
    }
}
