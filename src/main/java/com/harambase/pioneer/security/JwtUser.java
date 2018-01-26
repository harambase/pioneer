package com.harambase.pioneer.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtUser implements UserDetails {

    private final String userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String status;
    private final String profile;
    private final String type;

    public JwtUser(
          String userId,
          String username,
          String firstName,
          String lastName,
          String email,
          String password,
          Collection<? extends GrantedAuthority> authorities,
          String status,
          String profile,
          String type
    ) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.status = status;
        this.profile = profile;
        this.type = type;
    }

    @JsonIgnore
    public String getId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return username;
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
        return status.equals("1");
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStatus() {
        return status;
    }

    public String getProfile() {
        return profile;
    }

    public String getType() {
        return type;
    }
}
