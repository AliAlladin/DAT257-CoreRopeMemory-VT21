package com.CoreRopeMemory.TAPortal.model;

public class User {
    private int pNumber;
    private String email;
    private String familyName;
    private String firstName;
    private String streetAddr;
    private int postcode ;
    private String city;
    private boolean haveMaster;

    public int getpNumber() {
        return pNumber;
    }

    public void setpNumber(int pNumber) {
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

    public boolean isHaveMaster() {
        return haveMaster;
    }

    public void setHaveMaster(boolean haveMaster) {
        this.haveMaster = haveMaster;
    }
}
