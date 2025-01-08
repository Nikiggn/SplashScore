package com.splashScore.waterpolo_app.data.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "birth_date")
    private Instant birthDate;

    @Column
    private Integer age;

    @Column(name = "primary_position")
    private String primaryPosition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "club_id")
    private Club club;

    //private PlayerStats playerStats;

    @ManyToOne(optional = false)
    private Country country;

    //private Set<Match> matches;

    public Player() {
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getPrimaryPosition() {
        return primaryPosition;
    }

    public void setPrimaryPosition(String primaryPosition) {
        this.primaryPosition = primaryPosition;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
