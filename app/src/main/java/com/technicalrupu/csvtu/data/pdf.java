package com.technicalrupu.csvtu.data;

public class pdf {
    String UnitTittle;
    String tittle;
    String link;

    public pdf(String unitTittle, String tittle, String link) {
        UnitTittle = unitTittle;
        this.tittle = tittle;
        this.link = link;
    }
    public String getUnitTittle() {
        return UnitTittle;
    }

    public void setUnitTittle(String unitTittle) {
        UnitTittle = unitTittle;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
