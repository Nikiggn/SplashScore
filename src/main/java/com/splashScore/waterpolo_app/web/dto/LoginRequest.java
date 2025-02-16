package com.splashScore.waterpolo_app.web.dto;

import jakarta.validation.constraints.Size;

public class LoginRequest {

    @Size(min = 6, max = 15, message = "Username must be between 6 and 15 characters long")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 symbols")
    private String password;

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
