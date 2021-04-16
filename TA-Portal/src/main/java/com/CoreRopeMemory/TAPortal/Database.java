package com.CoreRopeMemory.TAPortal;

import com.CoreRopeMemory.TAPortal.model.WorkShift;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<WorkShift> workShifts = new ArrayList<>();

    public static List<WorkShift> getWorkShifts(){
        return workShifts;
    }

    public static void addWorkShift(WorkShift workShift){
        workShifts.add(workShift);
    }
}
