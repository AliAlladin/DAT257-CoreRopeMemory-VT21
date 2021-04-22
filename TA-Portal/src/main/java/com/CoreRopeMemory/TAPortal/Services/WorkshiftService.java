package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.WorkshiftRepository;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
