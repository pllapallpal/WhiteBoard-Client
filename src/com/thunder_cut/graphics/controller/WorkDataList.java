/*
 * WorkDataList.java
 * Author : Cwhist
 * Created Date : 2020-01-30
 */
package com.thunder_cut.graphics.controller;

import java.util.ArrayList;

public class WorkDataList {
    private final int MAXSIZE = 10;
    private ArrayList<WorkUnitData> workDataList = new ArrayList<>();
    private int presentIndex = 0;

    public void add(WorkUnitData workUnitData) {
        if(workDataList.size()==MAXSIZE) {
            workDataList.remove(0);
        }
        else {
            presentIndex++;
        }
        workDataList.add(workUnitData);
    }

    public void setPresentIndex(int num) {
        presentIndex += num;
    }

    public void remove() {
        for(int i=workDataList.size()-1; i>presentIndex; i--) {
            workDataList.remove(i);
        }
    }

    public boolean isRearWorkExist() {
        return presentIndex != (workDataList.size() - 1);
    }

    public WorkUnitData getWorkUnitData() {
        return workDataList.get(presentIndex);
    }
}
