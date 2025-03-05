package com.splashScore.waterpolo_app.security;

import com.splashScore.waterpolo_app.user.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

// ПАЗИ ДАННИТЕ НА ЛОГНАТИЯ ПОТРЕБИТЕЛ
public class AuthenticationMetaData implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private UserRole role;
    private LocalDateTime createdOn;

    public AuthenticationMetaData(Long id, String username, String password, String email , UserRole role, LocalDateTime createdOn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdOn = createdOn;
    }

    // Този метод се използва от SpringSecurity, за да се разбере какви  rolesAuthorities user-a има
    // Authority -> permission / role
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }
}

