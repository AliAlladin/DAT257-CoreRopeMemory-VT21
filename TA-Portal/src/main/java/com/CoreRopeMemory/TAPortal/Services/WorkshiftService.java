package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.WorkshiftRepository;
import com.CoreRopeMemory.TAPortal.model.Course;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WorkshiftService {
    /**
     * Inject workshift repository
     */
    @Autowired
    private WorkshiftRepository repo;

    /**
     * Returns all workshifts from the database
     * @return
     */
    public List<WorkShift> listALl(){
        List<WorkShift> workshifts = repo.findAll();
        Collections.sort(workshifts);
        Collections.reverse(workshifts);
        return workshifts;
    }

    /**
     * Returns all workshifts belonging to a user
     * @param email the email of the user
     * @return
     */
    public List<WorkShift> listByUser(String email){
        List<WorkShift> workshifts = listALl();
        List<WorkShift> workshiftsUser = new ArrayList<WorkShift>();
        for (WorkShift workShift : workshifts){
            if (workShift.getTa().getEmail().equals(email)){
                workshiftsUser.add(workShift);
            }
        }
        return workshiftsUser;
    }

    /**
     * Saves a workshift to the database
     * @param workshift the workshift to save
     */
    public void save (WorkShift workshift){
        repo.save(workshift);
    }

    public WorkShift getWorkshift(long id){
        Optional<WorkShift> optional = repo.findById(id);
        WorkShift workshift = null;
        if(optional.isPresent()){
            workshift = optional.get();
        } else {
            throw new RuntimeException("Workshift not found by id ::" + id);
        }
        return workshift;
    }


    /**
     *
     * @param email
     * @return
     */
    public List<Integer> getYearsWorked(String email){
        List<WorkShift> workShifts = listByUser(email);
        List<Integer> years = new ArrayList<>();
        for (WorkShift workShift : workShifts){
            if (!years.contains(workShift.getDate().getYear())){
                years.add(workShift.getDate().getYear());
            }
        }
        return years;
    }

    /**
     * returns a list with all the workshifts belonging to a user for a certain month
     * @param month specific month
     * @param email email of the user
     * @return
     */
    public List<WorkShift> listByMonth(Month month, String email, int year) {
        List<WorkShift> workshifts = listByUser(email);
        List<WorkShift> workshiftsMonth = new ArrayList<WorkShift>();
        for (WorkShift workshift : workshifts) {
            if (workshift.getDate().getMonth() == month && workshift.getDate().getYear() == year)
                workshiftsMonth.add(workshift);

        }
        return workshiftsMonth;
    };

    /**
     * returns a list with all the workshifts belonging to a user for a certain month
     * @param course    course that the workshifts should belong to.
     * @param month     specific month
     * @param email     email of the user
     * @return
     */
    public List<WorkShift> listByCourse(String course, Month month, String email, int year) {
        List<WorkShift> workshifts = listByMonth(month, email, year);
        List<WorkShift> workshiftsCourse = new ArrayList<>();
        for (WorkShift workshift : workshifts) {
            if (workshift.getCourse().getCourseCode().equals(course)) {
                workshiftsCourse.add(workshift);
            }
        }
        return workshiftsCourse;
    };

    public void update(WorkShift workshift){
        repo.save(workshift);
    }

    public void delete(Long id){
        repo.deleteById(id);
    }


}
