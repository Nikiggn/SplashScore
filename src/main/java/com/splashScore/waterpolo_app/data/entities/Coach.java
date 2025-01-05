package com.splashScore.waterpolo_app.data.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coaches")
public class Coach extends BaseEntity {

    @Column(unique = true, nullable = false, name = "full_name")
    private String fullName;

    @ManyToOne(optional = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "club_id")  // Foreign key to Club
    private Club club;

    @Column(nullable = false)
    private boolean isHeadCoach;

    public Coach() {
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public boolean isHeadCoach() {
        return isHeadCoach;
    }

    public void setHeadCoach(boolean headCoach) {
        isHeadCoach = headCoach;
    }
}
