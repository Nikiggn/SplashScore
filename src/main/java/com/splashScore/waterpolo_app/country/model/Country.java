package com.splashScore.waterpolo_app.country.model;

import com.splashScore.waterpolo_app.data.entities.BaseEntity;
import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.coach.model.Coach;
import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.player.model.Player;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true)
    private String code;

    private String flagUrl;

    @OneToMany(targetEntity = Player.class, mappedBy = "country")
    private Set<Player> players;

    @OneToMany(targetEntity = Coach.class, mappedBy = "country")
    private Set<Coach> coaches;

    @OneToMany(targetEntity = Club.class, mappedBy = "country")
    private Set<Club> clubs;

    @OneToMany(targetEntity = Referee.class, mappedBy = "country")
    private Set<Referee> referees;

    public Country() {
        players = new HashSet<>();
        coaches = new HashSet<>();
        clubs = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public Set<Club> getClubs() {
        return clubs;
    }

    public void setClubs(Set<Club> clubs) {
        this.clubs = clubs;
    }

    public Set<Referee> getReferees() {
        return referees;
    }

    public void setReferees(Set<Referee> referees) {
        this.referees = referees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
