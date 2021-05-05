package com.CoreRopeMemory.TAPortal.Repositories;

import com.CoreRopeMemory.TAPortal.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
}
