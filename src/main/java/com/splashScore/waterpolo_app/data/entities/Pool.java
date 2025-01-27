package com.splashScore.waterpolo_app.data.entities;

import com.splashScore.waterpolo_app.match.model.Match;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "pools")
public class Pool extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String town;

    @OneToMany(mappedBy = "pool")
    private Set<Match> matches;

    public Pool() {
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

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }
}
