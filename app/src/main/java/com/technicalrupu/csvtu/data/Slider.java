package com.technicalrupu.csvtu.data;

public class Slider {
    String status;
    String imglink;
    public Slider(String status,String imglink) {
        this.status=status;
        this.imglink = imglink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }
}
