package com.harambase.pioneer.server.pojo.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "advisorview")
public class AdvisorView {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "num_student")
    private Integer numStudent;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "profile")
    private String profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumStudent() {
        return numStudent;
    }

    public void setNumStudent(Integer numStudent) {
        this.numStudent = numStudent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
