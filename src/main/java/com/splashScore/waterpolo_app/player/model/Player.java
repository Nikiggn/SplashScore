package com.splashScore.waterpolo_app.player.model;

 import com.splashScore.waterpolo_app.club.model.Club;
 import jakarta.persistence.*;
 import java.time.LocalDate;
 import java.util.UUID;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String capNumber;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int matchesPlayed;

    private int age;

    private String profilePictureURL;

    @ManyToOne(optional = false)
    @JoinColumn(name = "club_id")
    private Club club;

    //private Set<Match> matches;

    public Player() {
    }

    public Player(UUID id, String name, LocalDate date, String capNumber, Country country, Status status, Club club) {
        this.id = id;
        this.fullName = name;
        this.birthDate = date;
        this.capNumber = capNumber;
        this.country = country;
        this.status = status;
        this.club = club;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }


    public String getCapNumber() {
        return capNumber;
    }

    public void setCapNumber(String capNumber) {
        this.capNumber = capNumber;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
