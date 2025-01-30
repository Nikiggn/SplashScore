package com.splashScore.waterpolo_app.web.dto;


import jakarta.validation.constraints.Size;

public class RegisterRequest {
    // когато някой ми изпраща тези данни -> да ги валидираме


    @Size(min = 6, max = 15)
    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    public RegisterRequest() {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
