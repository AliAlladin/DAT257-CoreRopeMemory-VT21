package com.CoreRopeMemory.TAPortal.model;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.time.LocalDate;
import com.CoreRopeMemory.TAPortal.model.Role;

/**
 * Class representing a user of the application
 */
@Entity
@Table(name = "TA")
public class User {
    @Id
    @Column(
            name = "p_number",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String pNumber;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String familyName;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String streetAddr;

    @Column(
            nullable = false,
            columnDefinition = "INTEGER"
    )
    private int postcode ;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String city;

    /**
     * True if user has a masters degree
     */
    @Column(
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private boolean hasMaster;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "userRoles", joinColumns = @JoinColumn(name = "UserId", referencedColumnName = "p_number"), inverseJoinColumns = @JoinColumn(name = "RoleId", referencedColumnName = "Id"))
    private List<Role> roles = new ArrayList<>();

    @Transient
    private double SALARY = 156;
    @Transient
    private double MASTER_SALARY = 200;

    /**
     * All the workshifts of the TA
     */
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "ta")
    private List<WorkShift> workshifts = new ArrayList<>();

    public User(String pNumber,
                String email,
                String familyName,
                String firstName,
                String streetAddr,
                int postcode,
                String city,
                boolean hasMaster,
                String password) {
        this.pNumber = pNumber;
        this.email = email;
        this.familyName = familyName;
        this.firstName = firstName;
        this.streetAddr = streetAddr;
        this.postcode = postcode;
        this.city = city;
        this.hasMaster = hasMaster;
        this.password = password;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getStreetAddr() {
        return streetAddr;
    }

    public void setStreetAddr(String streetAddr) {
        this.streetAddr = streetAddr;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isHasMaster() {
        return hasMaster;
    }

    public void setMaster(boolean haveMaster) {
        this.hasMaster = haveMaster;
    }

    public void addWorkshift(WorkShift workShift){
        workshifts.add(workShift);
    }

    public List<WorkShift> getWorkshifts() {
        return workshifts;
    }

    public void setRoles(List<Role> roles){
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "pNumber='" + pNumber + '\'' +
                ", email='" + email + '\'' +
                ", familyName='" + familyName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", streetAddr='" + streetAddr + '\'' +
                ", postcode=" + postcode +
                ", city='" + city + '\'' +
                ", hasMaster=" + hasMaster +
                ", workshifts=" + workshifts +
                '}';
    }

    public double totalHoursWorked(List<WorkShift> workshifts){
        double sum = 0;
        for (WorkShift workshift : workshifts){
            double add= workshift.getStartTime().until(workshift.getEndTime(), ChronoUnit.MINUTES);
            sum += add/60;
        }
        return sum;
    }

    public double getOvertimeHours(List<WorkShift> workshifts){
        double sum = 0;
        for (WorkShift workshift : workshifts){
            DayOfWeek day = workshift.getDate().getDayOfWeek();
            if (!workshift.getType().equals("Exam grading")) {
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    double add = workshift.getStartTime().until(workshift.getEndTime(), ChronoUnit.MINUTES);
                    sum += add / 60;
                } else {
                    LocalTime startOfOvertime = LocalTime.of(18, 0);
                    if (workshift.getStartTime().isAfter(startOfOvertime)) {
                        double add = workshift.getStartTime().until(workshift.getEndTime(), ChronoUnit.MINUTES);
                        sum += add / 60;
                    } else if (workshift.getEndTime().isAfter(startOfOvertime)) {
                        double add = startOfOvertime.until(workshift.getEndTime(), ChronoUnit.MINUTES);
                        sum += add / 60;
                    }
                }
            }
        }

        return sum;

    }

    public double getSalary(List<WorkShift> workshifts){
        double salary = 0;
        double overTimeHours = getOvertimeHours(workshifts);
        double totalHours = totalHoursWorked(workshifts);
        if (hasMaster){
        salary += overTimeHours * MASTER_SALARY * 1.5;
        salary += (totalHours - overTimeHours) * MASTER_SALARY;
        }else{
            salary += overTimeHours * SALARY * 1.5;
            salary += (totalHours - overTimeHours) * SALARY;
        }

        return salary;
    }

}
