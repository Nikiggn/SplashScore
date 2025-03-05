package com.splashScore.waterpolo_app.club.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.repository.ClubRepository;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public ClubService(ClubRepository clubRepository, ModelMapper modelMapper) {
        this.clubRepository = clubRepository;
        this.modelMapper = modelMapper;
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long clubId) {
        return clubRepository.findById(clubId).orElseThrow();
    }

    public void saveNewClub(AddClubRequest newClubRequest) {
        Club club = modelMapper.map(newClubRequest, Club.class);

        clubRepository.save(club);
    }

    @Transactional
    public void deleteClubById(Long id) {
        Club club = clubRepository.findById(id).orElseThrow();
        clubRepository.delete(club);
    }
}
