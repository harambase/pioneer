package com.harambase.pioneer.pojo.dto;

public class Option {

    private boolean prereq;

    private boolean time;

    private boolean capacity;

    public boolean isPrereq() {
        return prereq;
    }

    public void setPrereq(boolean prereq) {
        this.prereq = prereq;
    }

    public boolean isTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    public boolean isCapacity() {
        return capacity;
    }

    public void setCapacity(boolean capacity) {
        this.capacity = capacity;
    }


}
