package com.splashScore.waterpolo_app.club.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
}
