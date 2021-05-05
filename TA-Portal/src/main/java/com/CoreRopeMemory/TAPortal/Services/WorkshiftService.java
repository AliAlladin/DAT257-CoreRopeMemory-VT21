package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.WorkshiftRepository;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
        return repo.findAll();
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
     * returns a list with all the workshifts belonging to a user for a certain month
     * @param month specific month
     * @param email email of the user
     * @return
     */
    public List<WorkShift> listByMonth(Month month, String email) {
        List<WorkShift> workshifts = listByUser(email);
        List<WorkShift> workshiftsMonth = new ArrayList<WorkShift>();
        for (WorkShift workshift : workshifts) {
            if (workshift.getDate().getMonth() == month)
                workshiftsMonth.add(workshift);

        }
        return workshiftsMonth;
    };

    public void update(WorkShift workshift){
        //Id belongs to the old workshift to be deleted. 
        repo.save(workshift);
    }

    public void delete(Long id){
        repo.deleteById(id);
    }


}
