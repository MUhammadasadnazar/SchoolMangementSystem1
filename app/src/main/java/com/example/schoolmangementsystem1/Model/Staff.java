package com.example.schoolmangementsystem1.Model;

public class Staff {
    String StaffName;
    String StaffId;
    String ContactNo;
    String Address;
    String EmailAddress;
    String Education;
    boolean isAdmin;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Staff() {
    }
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }



    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }
}
