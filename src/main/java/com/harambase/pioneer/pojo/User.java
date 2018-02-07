package com.harambase.pioneer.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.harambase.pioneer.common.constant.RoleType;
import com.harambase.pioneer.server.pojo.base.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails, Serializable {

    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String status;
    private String type;
    private String roleId;

    private List<Authority> authorities;

    public User(Person person) {
        this.password = person.getPassword();
        this.userId = person.getUserId();
        this.username = person.getUsername();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.status = person.getStatus();
        this.type = person.getType();
        this.roleId = person.getRoleId();
        this.authorities = new ArrayList<>();
        for (String role_id : roleId.split("/")) {
            if (StringUtils.isNotEmpty(role_id)) {
                Authority authority = new Authority();
                authority.setId(Long.parseLong(role_id));
                switch (role_id){
                    case "1":
                        authority.setName(RoleType.USER.getRoleName());
                        break;
                    case "2":
                        authority.setName(RoleType.ADMIN.getRoleName());
                        break;
                    case "3":
                        authority.setName(RoleType.TEACH.getRoleName());
                        break;
                    case "4":
                        authority.setName(RoleType.LOGISTIC.getRoleName());
                        break;
                    case "5":
                        authority.setName(RoleType.SYSTEM.getRoleName());
                        break;
                    case "6":
                        authority.setName(RoleType.STUDENT.getRoleName());
                        break;
                    case "7":
                        authority.setName(RoleType.FACULTY.getRoleName());
                        break;
                    case "8":
                        authority.setName(RoleType.ADVISOR.getRoleName());
                        break;

                }
                authorities.add(authority);
            }
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
