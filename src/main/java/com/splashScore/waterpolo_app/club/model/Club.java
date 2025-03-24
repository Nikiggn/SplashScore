package com.splashScore.waterpolo_app.club.model;

import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.player.model.Player;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String town;

    @Column()
    @Enumerated(EnumType.STRING)
    private Country country;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Player> squad;

    private String logoUrl;

//    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Coach> coaches = new HashSet<>();

//    @OneToMany(mappedBy = "homeClub", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Match> homeMatches = new HashSet<>();
//
//    @OneToMany(mappedBy = "awayClub", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Match> awayMatches = new HashSet<>();

    public Club() {
    }

    public Club(UUID id, String name, String town, Country country) {
        this.id = id;
        this.name = name;
        this.town = town;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<Player> getSquad() {
        return squad;
    }

    public void setSquad(List<Player> squad) {
        this.squad = squad;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
