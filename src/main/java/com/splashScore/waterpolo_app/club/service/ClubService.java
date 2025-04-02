package com.splashScore.waterpolo_app.club.service;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.repository.ClubRepository;
import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.match.client.MatchClient;
import com.splashScore.waterpolo_app.match.dto.MatchCreation;
import com.splashScore.waterpolo_app.match.service.MatchService;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final MatchClient matchClient;
    private final ModelMapper modelMapper;

    @Autowired
    public ClubService(ClubRepository clubRepository, MatchClient matchClient, ModelMapper modelMapper ) {
        this.clubRepository = clubRepository;
        this.matchClient = matchClient;
        this.modelMapper = modelMapper;
     }

    public List<Club> getAllClubs() {
        return clubRepository.findAll().stream().sorted(Comparator.comparing(Club::getTown)).collect(Collectors.toList());
    }

    @Transactional
    public List<Club> getAllClubsSortedByPoints() {

        return clubRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Club::getPoints).reversed())
                .peek(club -> {
                    if (club.getLogoUrl() == null || club.getLogoUrl().length() > 19) {
                        club.setLogoUrl("/images/logo-1.jpg");
                    }
                })
                .collect(Collectors.toList());
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

        if (!matchClient.getMatchesByClubId(clubId).isEmpty()) {
            throw new DomainException(String.format("You cannot delete %s because it already participate in matches", club.getName()));
        }

        clubRepository.delete(club);
    }


    public List<MatchCreation> getClubMatches(UUID clubId) {
        return matchClient.getMatchesByClubId(clubId);
    }

    @Transactional
    public void save(Club club) {
        clubRepository.save(club);
    }
}
