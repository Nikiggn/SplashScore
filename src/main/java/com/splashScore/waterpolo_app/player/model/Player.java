package com.splashScore.waterpolo_app.player.model;

import com.splashScore.waterpolo_app.data.entities.BaseEntity;
import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.country.model.Country;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
