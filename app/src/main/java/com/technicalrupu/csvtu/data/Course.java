package com.technicalrupu.csvtu.data;

import java.util.ArrayList;
import java.util.List;

public class Course {
     String status;
     ArrayList<String> cname;
     ArrayList<semester> semoryear;


    public Course(String status, ArrayList<String> cname, ArrayList<semester> semoryear) {
        this.status = status;
        this.cname = cname;
        this.semoryear = semoryear;
    }
    public Course( String status, ArrayList<semester> semoryear) {
        this.status = status;
        this.semoryear = semoryear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getCname() {
        return cname;
    }

    public void setCname(ArrayList<String> cname) {
        this.cname = cname;
    }

    public ArrayList<semester> getSemoryear() {
        return semoryear;
    }

    public void setSemoryear(ArrayList<semester> semoryear) {
        this.semoryear = semoryear;
    }
}
