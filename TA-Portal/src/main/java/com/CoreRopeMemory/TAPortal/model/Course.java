package com.CoreRopeMemory.TAPortal.model;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Class representing a user of the application
 */
@Entity
@Table(name = "course")
public class Course {
    
    @Id
    @Column(
            name = "course_code",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String courseCode;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    /**
     * All the TAs in the course
     */
    @ManyToMany
    @JoinTable(name="course_users")
    private List<User> users = new ArrayList<>();

    /**
     * All the workshifts of the course
     */
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "course")
    private List<WorkShift> workshifts = new ArrayList<>();

    public Course(String courseCode,
                String name) {
        this.courseCode = courseCode;
        this.name = name;
    }

    public Course() {

    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public void addUser(User user) {
        users.add(user);
    }*/

    /*
    public void addWorkshift(WorkShift workShift){
        workshifts.add(workShift);
    }*/

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", name='" + name + 
                '}';
    }

    public String toFancyString() {
        return courseCode + " - " + name;
    }

}
