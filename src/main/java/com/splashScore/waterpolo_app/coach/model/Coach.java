package com.splashScore.waterpolo_app.coach.model;

import com.splashScore.waterpolo_app.club.model.Club;
import jakarta.persistence.*;

@Entity
@Table(name = "coaches")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private CoachRole role;

    @ManyToOne
    @JoinColumn(name = "club_id")  // Foreign key to Club
    private Club club;

    @Column(nullable = false)
    private boolean isHeadCoach;

    public Coach() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public boolean isHeadCoach() {
        return isHeadCoach;
    }

    public CoachRole getRole() {
        return role;
    }

    public void setRole(CoachRole role) {
        this.role = role;
    }

    public void setHeadCoach(boolean headCoach) {
        isHeadCoach = headCoach;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
