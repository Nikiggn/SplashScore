package com.splashScore.waterpolo_app.player.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.model.Status;
import com.splashScore.waterpolo_app.player.repository.PlayerRepository;
import com.splashScore.waterpolo_app.web.dto.AddPlayerRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ClubService clubService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, ClubService clubService, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.clubService = clubService;
        this.modelMapper = modelMapper;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll()
                .stream()
                .peek(pl -> pl.setAge(Period.between(pl.getBirthDate(), LocalDate.now()).getYears()))
                .collect(Collectors.toList());
    }

     public void saveNewPlayer(AddPlayerRequest newPlayerRequest) {
        Club club = clubService.getClubById(newPlayerRequest.getClubId());
        Player player = modelMapper.map(newPlayerRequest, Player.class);

        player.setId(null);
        player.setClub(club);
        player.setStatus(Status.ACTIVE);

        playerRepository.save(player);
    }

    @Transactional
    public void changePlayerStatus(Long id) {
        Player player = playerRepository.findById(id).orElseThrow();
        if (player.getStatus() == Status.ACTIVE) {
            player.setStatus(Status.RETIRED);
        }else {
            player.setStatus(Status.ACTIVE);
        }
    }
}
