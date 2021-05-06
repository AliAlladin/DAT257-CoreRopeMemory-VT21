package com.CoreRopeMemory.TAPortal.Model;

import com.CoreRopeMemory.TAPortal.model.User;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class UserTests {

    private List<WorkShift> workshifts = new ArrayList<>();
    private User user = new User();

    void populateWorkshifts(){
        workshifts.add(new WorkShift(LocalDate.of(2021,5,6), LocalTime.of(10,0), LocalTime.of(11,0), "Lectures and exercise sessions"));
        workshifts.add(new WorkShift(LocalDate.of(2021,5,6), LocalTime.of(11,15), LocalTime.of(12,0), "Lectures and exercise sessions"));
        workshifts.add(new WorkShift(LocalDate.of(2021,5,6), LocalTime.of(13,35), LocalTime.of(14,35), "Lectures and exercise sessions"));
        workshifts.add(new WorkShift(LocalDate.of(2021,5,8), LocalTime.of(13,35), LocalTime.of(14,35), "Lectures and exercise sessions"));
        workshifts.add(new WorkShift(LocalDate.of(2021,5,6), LocalTime.of(17,00), LocalTime.of(19,0), "Lectures and exercise sessions"));
    }

    @Before
    public void init(){
        populateWorkshifts();
        user.setHasMaster(false);
    }

    @Test
    public void salaryTest1(){
        assertTrue(user.getSalary(workshifts) == 1053);
    }

    @Test
    public void salaryTest2(){
        user.setHasMaster(true);
        assertTrue(user.getSalary(workshifts) == 1350);
    }

    @Test
    public void totalHoursWorkedTest(){
        assertTrue(user.totalHoursWorked(workshifts) == 5.75);

    }

    @Test
    public void getOvertimeHoursTest(){
        assertTrue(user.getOvertimeHours(workshifts) == 2);
    }


}
