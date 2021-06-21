package com.technicalrupu.csvtu.data;

public class Subject {
    String status;
    String sname;
    String link;
    String sid;

    public Subject(String status,String sid,String sname, String link) {
        this.sname = sname;
        this.link = link;
        this.sid = sid;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
