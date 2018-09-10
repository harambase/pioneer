package com.harambase.pioneer.server.pojo.dto;

import com.harambase.pioneer.server.helper.Name;

import java.io.Serializable;

public class TranscriptReportOnly implements Serializable {

    @Name("课程名")
    private String cname;

    @Name("学生名")
    private String sname;

    @Name("教师名")
    private String fname;

    @Name("成绩")
    private String grade;

    @Name("完成情况")
    private String complete;

    @Name("获得学分")
    private Double credit;

    @Name("评语")
    private String remark;


    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
