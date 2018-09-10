package com.harambase.pioneer.server.pojo.view;

import javax.persistence.*;

@Entity
@Table(name = "tempadviseview")
public class TempAdviseView {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "stu_name")
    private String stuName;

    @Column(name = "f_name")
    private String fname;

    @Column(name = "s_name")
    private String sname;

    @Column(name = "t_name")
    private String tname;

    @Column(name = "o_name")
    private String oname;

    @Column(name = "stu_profile")
    private String stuprofile;

    @Column(name = "f_profile")
    private String fprofile;

    @Column(name = "s_profile")
    private String sprofile;

    @Column(name = "t_profile")
    private String tprofile;

    @Column(name = "first_id")
    private String firstId;

    @Column(name = "second_id")
    private String secondId;

    @Column(name = "third_id")
    private String thirdId;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "status")
    private String status;

    @Column(name = "info")
    private String info;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
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

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getStuprofile() {
        return stuprofile;
    }

    public void setStuprofile(String stuprofile) {
        this.stuprofile = stuprofile;
    }

    public String getFprofile() {
        return fprofile;
    }

    public void setFprofile(String fprofile) {
        this.fprofile = fprofile;
    }

    public String getSprofile() {
        return sprofile;
    }

    public void setSprofile(String sprofile) {
        this.sprofile = sprofile;
    }

    public String getTprofile() {
        return tprofile;
    }

    public void setTprofile(String tprofile) {
        this.tprofile = tprofile;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
