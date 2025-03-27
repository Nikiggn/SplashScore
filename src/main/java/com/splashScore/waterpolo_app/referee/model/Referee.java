package com.splashScore.waterpolo_app.referee.model;

 import com.splashScore.waterpolo_app.player.model.Country;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
 import java.util.UUID;

@Entity
@Table(name = "referees")
public class Referee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int refereeAttendance;

    public Referee() {
    }

    public Referee(UUID id, String name, Country country, com.splashScore.waterpolo_app.referee.model.Status status) {
        this.id = id;
        this.fullName = name;
        this.country = country;
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRefereeAttendance() {
        return refereeAttendance;
    }

    public void setRefereeAttendance(int refereeAttendance) {
        this.refereeAttendance = refereeAttendance;
    }
}
