package com.harambase.pioneer.server.pojo.base;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tempcourse")
public class TempCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "crn")
    private String crn;

    @Column(name = "status")
    private String status;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "faculty_id")
    private String facultyId;

    @Column(name = "course_json")
    private String courseJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn == null ? null : crn.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    public String getCourseJson() {
        return courseJson;
    }

    public void setCourseJson(String courseJson) {
        this.courseJson = courseJson == null ? null : courseJson.trim();
    }

}