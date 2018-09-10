package com.harambase.pioneer.server.pojo.dto;

import com.harambase.pioneer.server.helper.Name;

public class AdviseReportOnly {

    @Name("学生名")
    private String sname;

    @Name("教师名")
    private String fname;

    @Name("学期")
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

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
