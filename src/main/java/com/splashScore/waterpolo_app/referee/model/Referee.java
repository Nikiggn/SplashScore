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

//    @ManyToMany(mappedBy = "referees",fetch = FetchType.EAGER)
//    private Set<Match> matches = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Status status;

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

//    public Set<Match> getMatches() {
//        return matches;
//    }
//
//    public void setMatches(Set<Match> matches) {
//        this.matches = matches;
//    }

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
}
