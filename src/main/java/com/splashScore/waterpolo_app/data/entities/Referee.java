package com.splashScore.waterpolo_app.data.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "referees")
public class Referee extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany(mappedBy = "referees")
    private Set<Match> matches;

    public Referee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }
}
