package com.splashScore.waterpolo_app.club.model;

import com.splashScore.waterpolo_app.coach.model.Coach;
import com.splashScore.waterpolo_app.country.model.Country;
import com.splashScore.waterpolo_app.data.entities.BaseEntity;
import com.splashScore.waterpolo_app.match.model.Match;
import com.splashScore.waterpolo_app.player.model.Player;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(optional = true)
    private Country country;

    private String logoUrl;

    @OneToOne
    private Coach headCoach;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coach> coaches = new HashSet<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Player> squad = new HashSet<>();

    private String town;

    private Instant established;

    private String description;

    @OneToMany(mappedBy = "homeClub", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Match> homeMatches = new HashSet<>();

    @OneToMany(mappedBy = "awayClub", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Match> awayMatches = new HashSet<>();

    public Club() {
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Instant getEstablished() {
        return established;
    }

    public void setEstablished(Instant established) {
        this.established = established;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coach getHeadCoach() {
        return headCoach;
    }

    public void setHeadCoach(Coach headCoach) {
        this.headCoach = headCoach;
    }

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public Set<Player> getSquad() {
        return squad;
    }

    public void setSquad(Set<Player> players) {
        this.squad = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
