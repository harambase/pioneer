package com.harambase.pioneer.pojo;

import java.io.Serializable;

public class Pin implements Serializable{

    private Integer id;

    private Integer pin;

    private String info;

    private String facultyid;

    private String studentid;

    private Integer role;

    private String starttime;

    private String endtime;

    private String createtime;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getFacultyid() {
        return facultyid;
    }

    public void setFacultyid(String facultyid) {
        this.facultyid = facultyid == null ? null : facultyid.trim();
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid == null ? null : studentid.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "Pin{" +
                "id=" + id +
                ", pin=" + pin +
                ", info='" + info + '\'' +
                ", facultyid='" + facultyid + '\'' +
                ", studentid='" + studentid + '\'' +
                ", role=" + role +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", createtime='" + createtime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}