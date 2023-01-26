package com.example.schoolmangementsystem1.Model;

public class Student {
    String stdName;
    String stdLastName;
    String stdID;
    String stdRollNo;
    String stdClassId;
    String stdMobileNo;
    String stdEmailAddres;
    String stdHomeAddress;
    String stdGaurdianName;
    String stdGaurdianPhoneNo;
    String stdPassword;


    public Student() {
    }

    public Student(String stdName, String stdLastName, String stdRollNo) {
        this.stdName = stdName;
        this.stdLastName = stdLastName;
        this.stdRollNo = stdRollNo;
    }

    public String getStdPassword() {
        return stdPassword;
    }

    public void setStdPassword(String stdPassword) {
        this.stdPassword = stdPassword;
    }

    public String getStdLastName() {
        return stdLastName;
    }

    public void setStdLastName(String stdLastName) {
        this.stdLastName = stdLastName;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdID() {
        return stdID;
    }

    public void setStdID(String stdID) {
        this.stdID = stdID;
    }

    public String getStdRollNo() {
        return stdRollNo;
    }

    public void setStdRollNo(String stdRollNo) {
        this.stdRollNo = stdRollNo;
    }

    public String getStdClassId() {
        return stdClassId;
    }

    public void setStdClassId(String stdClassId) {
        this.stdClassId = stdClassId;
    }

    public String getStdMobileNo() {
        return stdMobileNo;
    }

    public void setStdMobileNo(String stdMobileNo) {
        this.stdMobileNo = stdMobileNo;
    }

    public String getStdEmailAddres() {
        return stdEmailAddres;
    }

    public void setStdEmailAddres(String stdEmailAddres) {
        this.stdEmailAddres = stdEmailAddres;
    }

    public String getStdHomeAddress() {
        return stdHomeAddress;
    }

    public void setStdHomeAddress(String stdHomeAddress) {
        this.stdHomeAddress = stdHomeAddress;
    }

    public String getGaurdianName() {
        return stdGaurdianName;
    }

    public void setGaurdianName(String gaurdianName) {
        stdGaurdianName = gaurdianName;
    }

    public String getGaurdianPhoneNo() {
        return stdGaurdianPhoneNo;
    }

    public void setGaurdianPhoneNo(String gaurdianPhoneNo) {
        stdGaurdianPhoneNo = gaurdianPhoneNo;
    }
}
