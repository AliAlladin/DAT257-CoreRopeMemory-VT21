package com.CoreRopeMemory.TAPortal.Repositories;

import com.CoreRopeMemory.TAPortal.model.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkshiftRepository extends JpaRepository<WorkShift, Long> {


}
