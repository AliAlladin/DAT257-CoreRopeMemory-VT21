package com.CoreRopeMemory.TAPortal.Services;

import com.CoreRopeMemory.TAPortal.Repositories.CourseRepository;
import com.CoreRopeMemory.TAPortal.model.Course;
import com.CoreRopeMemory.TAPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User
 */

@Service
public class CourseService {
    /**
     * Injected User Repository
     */
    @Autowired
    private CourseRepository courseRepository;

    /**
     * Saves a user to the database
     * @param user the user to save
     */
    public void save(Course course){
        courseRepository.save(course);
    }

    /**
     * Gets the user with a specific personal number
     * @param courseCode the personal number of the user
     * @return the user
     */
    public Course get(String courseCode){
        return courseRepository.getOne(courseCode);
    }

    /**
     * Returns all courses from the database
     * @return
     */
    public List<Course> listALl(){
        return courseRepository.findAll();
    }

    /**
     * Checks if the user table in the database is empty
     * @return true if the are no users in the database
     */
    public boolean isEmpty() {
        if (courseRepository.count() == 0){
            return true;
        }
        return false;
    }
}
