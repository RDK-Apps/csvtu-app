package com.technicalrupu.csvtu.data;

public class Syllabus {
    String status;
    String SubjectName;
    String tittle;
    String bglink;
    String SyllabusLink;

    public Syllabus(String status,String subjectName, String tittle, String bglink, String syllabusLink) {
        this.status=status;
        SubjectName = subjectName;
        this.tittle = tittle;
        this.bglink = bglink;
        SyllabusLink = syllabusLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBglink() {
        return bglink;
    }

    public void setBglink(String bglink) {
        this.bglink = bglink;
    }

    public String getSyllabusLink() {
        return SyllabusLink;
    }

    public void setSyllabusLink(String syllabusLink) {
        SyllabusLink = syllabusLink;
    }
}
