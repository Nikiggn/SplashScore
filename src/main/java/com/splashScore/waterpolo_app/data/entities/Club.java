package com.splashScore.waterpolo_app.data.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clubs")
public class Club  extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Country country;

    @Column(name = "logo_url")
    private String logoUrl;

    @OneToOne
    private Coach headCoach;

    @OneToMany(mappedBy = "club")
    private Set<Coach> coaches;

    @OneToMany(mappedBy = "club")
    private Set<Player> squad;

    @Column()
    private String town;

    @Column
    private Instant established;

    @Column
    private String description;

    @OneToMany(mappedBy = "homeClub", cascade = CascadeType.ALL)
    private List<Match> homeMatches;

    @OneToMany(mappedBy = "awayClub", cascade = CascadeType.ALL)
    private List<Match> awayMatches;

    public Club() {
        coaches = new HashSet<>();
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
}
