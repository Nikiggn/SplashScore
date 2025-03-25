package com.splashScore.waterpolo_app.club;

import com.splashScore.waterpolo_app.club.model.Club;
import com.splashScore.waterpolo_app.club.repository.ClubRepository;
import com.splashScore.waterpolo_app.club.service.ClubService;
import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.web.dto.AddClubRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClubServiceUTest {

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClubService clubService;

    @Test
    void givenValidClubId_whenGettingClub_thenReturnClub() {
        UUID clubId = UUID.randomUUID();
        Club club = new Club();
        club.setId(clubId);

        when(clubRepository.findById(any(UUID.class))).thenReturn(Optional.of(club));

        assertThat(clubService.getClubById(clubId).getId()).isEqualTo(clubId);
    }

    @Test
    void givenInvalidClubId_whenGettingClub_thenThrowsException() {
        when(clubRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> clubService.getClubById(any()));
    }

    @Test
    void whenGettingAllClubs_thenReturnSortedClubs() {
        Club club1 = new Club();
        club1.setName("Club 1");
        club1.setTown("B Town");

        Club club2 = new Club();
        club2.setName("Club 2");
        club2.setTown("A Town");

        when(clubRepository.findAll()).thenReturn(List.of(club1, club2));

        List<Club> result = clubService.getAllClubs();

        assertThat(result).isEqualTo(List.of(club2, club1));
    }

    @Test
    void givenAddClubRequest_whenSavingNewClub_thenSavesAndReturnsClub() {
        AddClubRequest addClubRequest = new AddClubRequest();
        addClubRequest.setName("Ticha");
        addClubRequest.setTown("Varna");
        addClubRequest.setCountry(Country.BULGARIA);
        addClubRequest.setLogo_URL("https://logo.png");

        Club club = new Club();
        club.setName("Ticha");
        club.setTown("Varna");
        club.setCountry(Country.BULGARIA);
        club.setLogoUrl("https://logo.png");

        when(modelMapper.map(addClubRequest, Club.class)).thenReturn(club);
        when(clubRepository.save(club)).thenReturn(club);

        Club realClub =  clubService.saveNewClub(addClubRequest);

        assertThat(realClub).isNotNull();
        assertThat(realClub).isEqualTo(club);
    }

    @Test
    void givenInvalidClubId_whenDeletingClub_thenThrowsException() {
        when(clubRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> clubService.deleteClubById(any()));
    }

    @Test
    void givenValidClubId_whenDeletingClub_thenDeletesClub() {
        UUID clubId = UUID.randomUUID();
        Club club = new Club();
        club.setId(clubId);

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        clubService.deleteClubById(clubId);

        verify(clubRepository).delete(club);
    }





}
