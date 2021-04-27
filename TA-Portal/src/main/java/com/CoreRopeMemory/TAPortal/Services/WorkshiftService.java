package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.WorkshiftRepository;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<WorkShift> listByMonth(int month) {
        List<WorkShift> workshifts = listALl();
        List<WorkShift> workshiftsMonth = new ArrayList<WorkShift>();
        int wsMonth;
        for (WorkShift workshift : workshifts) {
            String date = workshift.getDate();
            CharSequence temp = date.subSequence(5, 7);
            if (temp.charAt(0) == '0')
                wsMonth = Character.getNumericValue(temp.charAt(1));
            else
                wsMonth = Integer.parseInt(temp.toString());
            if (wsMonth == month)
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
