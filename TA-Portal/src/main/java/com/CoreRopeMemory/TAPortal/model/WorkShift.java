package com.CoreRopeMemory.TAPortal.model;

import javax.persistence.*;

/**
 * Class representing a workshift
 */
@Entity
@Table(name = "work_shift")
public class WorkShift {
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

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String date;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String startTime;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String endTime;

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
    

    public WorkShift() {

    }

    public Long getId(){
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
}
