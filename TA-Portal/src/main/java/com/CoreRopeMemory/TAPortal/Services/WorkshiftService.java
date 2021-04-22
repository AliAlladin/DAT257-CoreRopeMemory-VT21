package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.WorkshiftRepository;
import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshiftService {
    @Autowired
    private WorkshiftRepository repo;

    public List<WorkShift> listALl(){
        return repo.findAll();
    }

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


}
