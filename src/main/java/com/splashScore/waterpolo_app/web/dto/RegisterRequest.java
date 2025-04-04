package com.splashScore.waterpolo_app.web.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @Size(min = 6, max = 15, message = "Username must be between 6 and 15 characters long")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;

    @Size(min = 6, message = "Password must be at least 6 symbols")
    private String password;


    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
