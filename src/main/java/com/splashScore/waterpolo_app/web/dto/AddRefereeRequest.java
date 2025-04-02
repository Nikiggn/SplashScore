package com.splashScore.waterpolo_app.web.dto;

import com.splashScore.waterpolo_app.player.model.Country;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AddRefereeRequest {

    @Size(min = 4, max = 15, message = "Full name must be least 4 characters long")
    private String fullName;

    private Country country;

    public AddRefereeRequest() {
    }

    public AddRefereeRequest(String fullName, Country country) {
        this.fullName = fullName;
        this.country = country;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
