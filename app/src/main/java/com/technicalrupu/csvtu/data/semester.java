package com.technicalrupu.csvtu.data;

public class semester {
    String cname;
    String semoryear;

    public semester(String cname, String semoryear) {
        this.cname = cname;
        this.semoryear = semoryear;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSemoryear() {
        return semoryear;
    }

    public void setSemoryear(String semoryear) {
        this.semoryear = semoryear;
    }
}

