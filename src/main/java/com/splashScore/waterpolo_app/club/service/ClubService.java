package com.splashScore.waterpolo_app.club.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.repository.ClubRepository;
import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return clubRepository.findAll().stream().sorted(Comparator.comparing(Club::getTown)).collect(Collectors.toList());
    }

    public Club getClubById(UUID clubId) {
        return clubRepository.findById(clubId).orElseThrow(() -> new DomainException(String.format("Club with such id does not exist: %s", clubId)));
    }

    public Club saveNewClub(AddClubRequest newClubRequest) {
        Club club = modelMapper.map(newClubRequest, Club.class);

        return clubRepository.save(club);
    }

    @Transactional
    public void deleteClubById(UUID clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new DomainException(String.format("Club with such id does not exist: %s", clubId)));
        clubRepository.delete(club);
    }
}
