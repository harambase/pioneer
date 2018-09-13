package com.harambase.pioneer.server.pojo.dto;

import com.harambase.pioneer.server.helper.Name;

public class AdviseReportOnly {

    @Name("学生名")
    private String sname;

    @Name("教师名")
    private String fname;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
