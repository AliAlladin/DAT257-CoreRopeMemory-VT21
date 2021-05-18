package com.CoreRopeMemory.TAPortal.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * Class representing a workshift
 */
@Entity
@Table(name = "work_shift")
public class WorkShift implements Comparable<WorkShift>{
    @Id
    @SequenceGenerator(name = "workshift_sequence",
                sequenceName = "workshift_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workshift_sequence"
    )
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            nullable = false,
            columnDefinition = "date"
    )
    private LocalDate date;

    @Column(
            nullable = false,
            columnDefinition = "time"
    )
    private LocalTime startTime;

    @Column(
            nullable = false,
            columnDefinition = "time"
    )
    private LocalTime endTime;

    @Column(
            nullable = false
    )
    private String type;

    @Column(
            columnDefinition = "TEXT"
    )
    private String comment;

    /**
     * The TA that has done the workshift
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta", referencedColumnName = "p_number", nullable = false)
    private User ta;

    /**
     * The course of the workshift
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course", referencedColumnName = "course_code", nullable = true)
    private Course course;

    public WorkShift() {

    }

    public WorkShift(LocalDate date, LocalTime startTime, LocalTime endTime, String type) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    public WorkShift(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId(){
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getTa() {
        return ta;
    }

    public void setTa(User ta) {
        this.ta = ta;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "WorkShift{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                ", ta=" + ta +
                '}';
    }

    @Override
    public int compareTo(WorkShift o) {
        return LocalDateTime.of(getDate(), getStartTime()).compareTo(LocalDateTime.of(o.getDate(), o.getStartTime()));
    }
}
