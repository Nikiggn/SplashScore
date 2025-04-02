package com.splashScore.waterpolo_app.web.dto;

import com.splashScore.waterpolo_app.player.model.Country;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class AddClubRequest {

    @Size(min = 4, max = 15, message = "Club's name must be between 4 and 15 characters!")
    private String name;

    @Size(min = 4, max = 15, message = "Club's town must be between 4 and 15 characters!")
    private String town;

    private Country country;

    @NotEmpty(message = "Logo of the club cannot be empty!")
    private String logo_URL;

    public AddClubRequest() {
    }

    public AddClubRequest(String name, String town, Country country, String logo_URL) {
        this.name = name;
        this.town = town;
        this.country = country;
        this.logo_URL = logo_URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


    public String getLogo_URL() {
        return logo_URL;
    }

    public void setLogo_URL(String logo_URL) {
        this.logo_URL = logo_URL;
    }
}
