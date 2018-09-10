package com.harambase.pioneer.server.pojo.base;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transcript")
public class Transcript implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "credit")
    private Double credit;

    @Column(name = "assign_time")
    private String assignTime;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "remark")
    private String remark;

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

}