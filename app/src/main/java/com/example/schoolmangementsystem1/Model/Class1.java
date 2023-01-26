package com.example.schoolmangementsystem1.Model;

public class Class1 {
    String ClassTitle;
    String InchargeName;
    String InchargeId;
    int noofStudents;
    String ClassID;

    public Class1() {
    }

    public String getInchargeId() {
        return InchargeId;
    }

    public void setInchargeId(String inchargeId) {
        InchargeId = inchargeId;
    }

    public String getClassTitle() {
        return ClassTitle;
    }

    public void setClassTitle(String classTitle) {
        ClassTitle = classTitle;
    }

    public String getInchargeName() {
        return InchargeName;
    }

    public void setInchargeName(String inchargeName) {
        InchargeName = inchargeName;
    }

    public int getNoofStudents() {
        return noofStudents;
    }

    public void setNoofStudents(int noofStudents) {
        this.noofStudents = noofStudents;
    }

    public String getClassID() {
        return ClassID;
    }

    public void setClassID(String classID) {
        ClassID = classID;
    }
}
