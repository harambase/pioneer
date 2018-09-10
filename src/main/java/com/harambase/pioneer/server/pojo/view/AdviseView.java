package com.harambase.pioneer.server.pojo.view;

import javax.persistence.*;

@Entity
@Table(name = "adviseview")
public class AdviseView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "faculty_id")
    private String facultyId;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "operator_id")
    private String operator_id;

    @Column(name = "fname")
    private String fname;

    @Column(name = "sname")
    private String sname;

    @Column(name = "oname")
    private String oname;

    @Column(name = "info")
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
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
