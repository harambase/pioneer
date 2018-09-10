package com.harambase.pioneer.server.pojo.base;

import javax.persistence.*;

@Entity
@Table(name = "tempadvise")
public class TempAdvise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "student_id")
    private String studentId;

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
