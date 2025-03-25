package com.splashScore.waterpolo_app.player;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.player.model.Player;
import com.splashScore.waterpolo_app.player.model.Status;
import com.splashScore.waterpolo_app.player.repository.PlayerRepository;
import com.splashScore.waterpolo_app.player.service.PlayerService;
import com.splashScore.waterpolo_app.web.dto.AddPlayerRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceUTests {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private ClubService clubService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlayerService playerService;

    //1999-06-09
    //2002-02-24

    @Test
    void whenGettingAllPlayers_thenReturnSortedListOfPlayers_withAge() {
        System.out.println();
        Club club = new Club();
        club.setName("A Club");

        Club club2 = new Club();
        club2.setName("B Club");

        // This needs to be first (2)
        Player player1 = new Player();
        player1.setClub(club);
        player1.setBirthDate(LocalDate.of(1999,6,9));
        player1.setStatus(Status.RETIRED);

        // This needs to be first (1)
        Player player2 = new Player();
        player2.setClub(club);
        player2.setBirthDate(LocalDate.of(2002,2,24));
        player2.setStatus(Status.ACTIVE);

        // This needs to be first (3)
        Player player3 = new Player();
        player3.setClub(club2);
        player3.setBirthDate(LocalDate.of(1999,6,9));

        when(playerRepository.findAll()).thenReturn(List.of(player1, player2, player3));

        List<Player> players = playerService.getAllPlayers();

        assertThat(players).isEqualTo(List.of(player2, player1, player3));
        assertThat(players.getFirst().getAge()).isEqualTo(Period.between(player2.getBirthDate(), LocalDate.now()).getYears());
    }

    @Test
    void givenAddPlayerRequest_whenSavingNewPlayer_thenReturnPlayerSaved() {
        Club club = new Club();
        club.setId(UUID.randomUUID());

        AddPlayerRequest addPlayerRequest = new AddPlayerRequest();
        addPlayerRequest.setFullName("Nikola");
        addPlayerRequest.setDateOfBirth(LocalDate.of(1999,6,9));
        addPlayerRequest.setClubId(club.getId());
        addPlayerRequest.setCountry(Country.BULGARIA);
        addPlayerRequest.setCapNumber("2");

        Player player = new Player();
        player.setId(UUID.randomUUID());
        player.setFullName("Nikola");
        player.setBirthDate(LocalDate.of(1999,6,9));
        player.setStatus(Status.ACTIVE);
        player.setClub(club);
        player.setCountry(Country.BULGARIA);
        player.setCapNumber("2");

        when(clubService.getClubById(club.getId())).thenReturn(club);
        when(modelMapper.map(addPlayerRequest, Player.class)).thenReturn(player);
        when(playerRepository.save(player)).thenReturn(player);


        Player realPlayer = playerService.saveNewPlayer(addPlayerRequest);

        assertThat(realPlayer).isNotNull();
        assertThat(realPlayer).isEqualTo(player);

    }

    @Test
    void givenInvalidPlayerId_whenChangingStatus_thenExceptionThrown() {
        when(playerRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> playerService.changePlayerStatus(UUID.randomUUID()));
    }

    @Test
    void givenPlayerId_whenChangingRetiredStatus_thenStatusIsChangedToActive() {
        Player player = new Player();
        player.setStatus(Status.RETIRED);

        when(playerRepository.findById(any())).thenReturn(Optional.of(player));
        playerService.changePlayerStatus(UUID.randomUUID());

        assertThat(Status.ACTIVE).isEqualTo(player.getStatus());
    }

    @Test
    void givenPlayerId_whenChangingActiveStatus_thenStatusIsChangedToRetired() {
        Player player = new Player();
        player.setStatus(Status.ACTIVE);

        when(playerRepository.findById(any())).thenReturn(Optional.of(player));
        playerService.changePlayerStatus(UUID.randomUUID());

        assertThat(Status.RETIRED).isEqualTo(player.getStatus());
    }

    @Test
    void whenCheckAvailableClubs_returnsEmptyList_thenExceptionThrown() {
        when(clubService.getAllClubs()).thenReturn(List.of());
        assertThrows(DomainException.class, () -> playerService.checkAvailableClubs());
    }

    @Test
    void  whenGettingPlayersByClubId_returnsListOfPlayers(){
        UUID clubId = UUID.randomUUID();
        Club club = new Club();
        club.setId(clubId);

        Player player1 = new Player();
        player1.setClub(club);

        Player player2 = new Player();
        player2.setClub(club);

        when(clubService.getClubById(clubId)).thenReturn(club);
        when(playerRepository.findByClubId(clubId)).thenReturn(List.of(player1, player2));

        List<Player> players = playerService.getPlayersByClub(clubId);

        assertThat(players).isEqualTo(List.of(player1, player2));

     }
}
