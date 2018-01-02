package com.harambase.pioneer.pojo;


import java.io.Serializable;

public class Student implements Serializable {


    private String studentId;

    private String sname;

    private String status;

    private String updaeTime;

    private int maxCredits;

    private int complete;

    private int progress;

    private int incomplete;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(int incomplete) {
        this.incomplete = incomplete;
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(int maxCredits) {
        this.maxCredits = maxCredits;
    }


    public String getUpdaeTime() {
        return updaeTime;
    }

    public void setUpdaeTime(String updaeTime) {
        this.updaeTime = updaeTime;
    }
}
