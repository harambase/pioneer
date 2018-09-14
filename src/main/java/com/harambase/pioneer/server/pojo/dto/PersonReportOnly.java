package com.harambase.pioneer.server.pojo.dto;

import com.harambase.pioneer.server.helper.Name;

import java.io.Serializable;

public class PersonReportOnly implements Serializable {

    @Name("用户ID/学号/工号")
    private String userId;

    @Name("姓")
    private String lastName;

    @Name("名")
    private String firstName;

    @Name("生日")
    private String birthday;

    @Name("电子邮箱")
    private String email;

    @Name("电话")
    private String tel;

    @Name("qq")
    private String qq;

    @Name("类型")
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
