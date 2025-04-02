package com.splashScore.waterpolo_app.web.dto;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.player.model.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class AddPlayerRequest {

    @Size(min = 4, max = 30, message = "Full name must be least 4 characters long")
    private String fullName;

    @NotNull(message = "Date of birth cannot be empty")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private UUID clubId;

    private Country country;

    private String capNumber;

    public AddPlayerRequest() {
    }

    public AddPlayerRequest(String nikola, LocalDate of, String number, Country country, Status status, UUID id) {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCapNumber() {
        return capNumber;
    }

    public void setCapNumber(String capNumber) {
        this.capNumber = capNumber;
    }

    public UUID getClubId() {
        return clubId;
    }

    public void setClubId(UUID clubId) {
        this.clubId = clubId;
    }
}
