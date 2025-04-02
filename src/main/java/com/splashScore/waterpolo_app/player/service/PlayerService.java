package com.splashScore.waterpolo_app.player.service;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.model.Status;
import com.splashScore.waterpolo_app.player.repository.PlayerRepository;
import com.splashScore.waterpolo_app.web.dto.AddPlayerRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
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
                .sorted(Comparator.comparing((Player p) -> p.getClub().getName())
                        .thenComparing(Player::getStatus))
                .toList();
    }

    public Player saveNewPlayer(AddPlayerRequest newPlayerRequest) {
        Club club = clubService.getClubById(newPlayerRequest.getClubId());
        Player player = modelMapper.map(newPlayerRequest, Player.class);

         player.setId(null);
        player.setClub(club);
        player.setStatus(Status.ACTIVE);

        return playerRepository.save(player);
    }

    @Transactional
    public void changePlayerStatus(UUID id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new DomainException("Player not found with id: " + id));
        if (player.getStatus() == Status.ACTIVE) {
            player.setStatus(Status.RETIRED);
        }else {
            player.setStatus(Status.ACTIVE);
        }
    }

    public void checkAvailableClubs(){
        if (clubService.getAllClubs().isEmpty()){
            throw new DomainException("There are no available clubs");
        }
    }

    public List<Player> getPlayersByClub(UUID clubId) {
        Club club = clubService.getClubById(clubId);

        return playerRepository.findByClubId(club.getId());
    }

    public List<Player> getPlayersByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("fullName").ascending());
        return playerRepository.findAll(pageable).getContent().stream().sorted(Comparator.comparing((Player p) -> p.getClub().getName())
                        .thenComparing(Player::getStatus))
                        .toList();
    }

    public int getTotalPlayerCount() {
        return (int) playerRepository.count();
    }
}
