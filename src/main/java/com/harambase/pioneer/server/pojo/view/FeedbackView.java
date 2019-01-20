package com.harambase.pioneer.server.pojo.view;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "feedbackview")
public class FeedbackView implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "faculty_id")
    private String facultyId;

    @Column(name = "info")
    private String info;

    @Column(name = "rate")
    private String rate;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "self_comment")
    private String selfComment;

    @Column(name = "comment")
    private String comment;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "fname")
    private String fname;

    @Column(name = "oname")
    private String oname;

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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSelfComment() {
        return selfComment;
    }

    public void setSelfComment(String selfComment) {
        this.selfComment = selfComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }
}
