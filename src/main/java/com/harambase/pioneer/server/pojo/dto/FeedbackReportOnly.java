package com.harambase.pioneer.server.pojo.dto;

import com.harambase.pioneer.server.helper.Name;

public class FeedbackReportOnly {

    @Name("教师名")
    private String fname;

    @Name("教师自评")
    private String selfCommnet;

    @Name("星级评分")
    private String rate;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSelfCommnet() {
        return selfCommnet;
    }

    public void setSelfCommnet(String selfCommnet) {
        this.selfCommnet = selfCommnet;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
