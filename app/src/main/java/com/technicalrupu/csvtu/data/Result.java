package com.technicalrupu.csvtu.data;

public class Result {
    String status;
    String tittle;
    String isseuedon;
    String otherinfo;
    String resultlink;
    public Result(String status,String tittle, String isseuedon, String otherinfo, String resultlink) {
        this.tittle = tittle;
        this.isseuedon = isseuedon;
        this.otherinfo = otherinfo;
        this.resultlink = resultlink;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getIssuedon() {
        return isseuedon;
    }

    public void setIssuedon(String issuedon) {
        this.isseuedon = issuedon;
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }

    public String getResultlink() {
        return resultlink;
    }

    public void setResultlink(String resultlink) {
        this.resultlink = resultlink;
    }
}
