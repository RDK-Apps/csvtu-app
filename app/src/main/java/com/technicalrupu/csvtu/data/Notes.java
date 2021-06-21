package com.technicalrupu.csvtu.data;

import java.util.ArrayList;

public class Notes {
    String status;
    ArrayList<String> unit;
    ArrayList<pdf> sublist;

    public Notes(String status,ArrayList<String> unit, ArrayList<pdf> sublist) {
        this.status=status;
        this.unit = unit;
        this.sublist = sublist;
    }
    public Notes(String status,ArrayList<pdf> sublist)
    {
        this.status=status;
        this.sublist = sublist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getUnit() {
        return unit;
    }

    public void setUnit(ArrayList<String> unit) {
        this.unit = unit;
    }

    public ArrayList<pdf> getSublist() {
        return sublist;
    }

    public void setSublist(ArrayList<pdf> sublist) {
        this.sublist = sublist;
    }
}
