package com.splashScore.waterpolo_app.referee.model;

 import com.splashScore.waterpolo_app.player.model.Country;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "referees")
public class Referee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
