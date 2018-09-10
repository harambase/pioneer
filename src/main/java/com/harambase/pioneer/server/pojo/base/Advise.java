package com.harambase.pioneer.server.pojo.base;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "advise")
public class Advise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "faculty_id")
    private String facultyId;

    @Column(name = "info")
    private String info;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "operatorId")
    private String operatorId;

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
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId == null ? null : facultyId.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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

}
