package com.harambase.pioneer.server.pojo.view;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studentview")
public class StudentView implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "sname")
    private String sname;

    @Column(name = "status")
    private String status;

    @Column(name = "max_credits")
    private Integer maxCredits;

    @Column(name = "complete")
    private Double complete;

    @Column(name = "progress")
    private Double progress;

    @Column(name = "incomplete")
    private Double incomplete;

    @Column(name = "info")
    private String info;

    @Column(name = "profile")
    private String profile;

    @Column(name = "trial_period")
    private String trialPeriod;

    @Column(name = "tel")
    private String tel;

    @Column(name = "qq")
    private String qq;

    @Column(name = "dorm")
    private String dorm;

    @Column(name = "gender")
    private String gender;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(String trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getDorm() {
        return dorm;
    }

    public void setDorm(String dorm) {
        this.dorm = dorm;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Double getComplete() {
        return complete;
    }

    public void setComplete(Double complete) {
        this.complete = complete;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Double getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(Double incomplete) {
        this.incomplete = incomplete;
    }

    public Integer getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(Integer maxCredits) {
        this.maxCredits = maxCredits;
    }


}
