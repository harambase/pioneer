package com.harambase.pioneer.server.pojo.view;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transcriptview")
public class TranscriptView implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "crn")
    private String crn;

    @Column(name = "grade")
    private String grade;

    @Column(name = "complete")
    private String complete;

    @Column(name = "assign_time")
    private String assignTime;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "credit")
    private Double credit;

    //Extension:
    @Column(name = "cname")
    private String cname;

    @Column(name = "faculty_id")
    private String facultyId;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "day")
    private String day;

    @Column(name = "time")
    private String time;

    @Column(name = "date")
    private String date;

    @Column(name = "sname")
    private String sname;

    @Column(name = "fname")
    private String fname;

    @Column(name = "info")
    private String info;

    @Column(name = "remark")
    private String remark;

    @Column(name = "sprofile")
    private String sprofile;

    @Column(name = "fprofile")
    private String fprofile;

    public String getSprofile() {
        return sprofile;
    }

    public void setSprofile(String sprofile) {
        this.sprofile = sprofile;
    }

    public String getFprofile() {
        return fprofile;
    }

    public void setFprofile(String fprofile) {
        this.fprofile = fprofile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn == null ? null : crn.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete == null ? null : complete.trim();
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime == null ? null : assignTime.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

}
