package com.splashScore.waterpolo_app.referee;

import com.splashScore.waterpolo_app.exception.DomainException;
import com.splashScore.waterpolo_app.exception.RefereeAlreadyExistException;
import com.splashScore.waterpolo_app.player.model.Country;
import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.referee.model.Status;
import com.splashScore.waterpolo_app.referee.repository.RefereeRepository;
import com.splashScore.waterpolo_app.referee.service.RefereeService;
import com.splashScore.waterpolo_app.web.dto.AddRefereeRequest;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefereeServiceUTests {
    @Mock
    private RefereeRepository refereeRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RefereeService refereeService;

    @Test
    void whenGettingAllReferees_thenReturnSortedList() {
        Referee referee1 = new Referee();
        referee1.setStatus(Status.ARCHIVED);

        Referee referee2 = new Referee();
        referee2.setStatus(Status.ACTIVE);

        when(refereeRepository.findAll()).thenReturn(List.of(referee1, referee2));
        List<Referee> refereeList = refereeService.getAllReferees();

        assertThat(refereeList).isEqualTo(List.of(referee2, referee1));
    }

    @Test
    void givenAddRefereeRequest_whenRefereeWithThatNameExists_thenThrowException() {
        AddRefereeRequest addRefereeRequest = new AddRefereeRequest();
        Referee referee = new Referee();

        when(refereeRepository.findByFullName(any())).thenReturn(Optional.of(referee));

        assertThrows(RefereeAlreadyExistException.class, () -> refereeService.saveNewReferee(addRefereeRequest));
    }

    @Test
    void givenValidAddRefereeRequest_whenSavingNewReferee_thenSavesAndReturnsReferee() {
        AddRefereeRequest addRefereeRequest = new AddRefereeRequest();
        addRefereeRequest.setFullName("John Doe");
        addRefereeRequest.setCountry(Country.BULGARIA);

        Referee referee = new Referee();
        referee.setFullName("John Doe");
        referee.setCountry(Country.BULGARIA);
        referee.setStatus(Status.ACTIVE);

        when(modelMapper.map(addRefereeRequest, Referee.class)).thenReturn(referee);
        when(refereeRepository.save(referee)).thenReturn(referee);

        Referee savedReferee = refereeService.saveNewReferee(addRefereeRequest);

        assertThat(savedReferee).isEqualTo(referee);
     }


    @Test
    void givenInvalidRefereeId_whenChangingStatus_thenThrowException() {
        when(refereeRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> refereeService.changeRefereeStatus(any()));
    }

    @Test
    void givenValidRefereeId_whenChangingActiveStatus_thenReceivesArchivedStatus() {
        Referee referee = new Referee();
        referee.setStatus(Status.ACTIVE);

        when(refereeRepository.findById(any())).thenReturn(Optional.of(referee));

        refereeService.changeRefereeStatus(any(UUID.class));

        assertThat(referee.getStatus()).isEqualTo(Status.ARCHIVED);
    }

    @Test
    void givenValidRefereeId_whenChangingArchivedStatus_thenReceivesActiveStatus() {
        Referee referee = new Referee();
        referee.setStatus(Status.ARCHIVED);

        when(refereeRepository.findById(any())).thenReturn(Optional.of(referee));

        refereeService.changeRefereeStatus(any(UUID.class));

        assertThat(referee.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    void whenGettingAllReferees_returnListWithOnlyActivesOnes(){
        Referee referee = new Referee(UUID.randomUUID(), "Nikola Ant", Country.BULGARIA, Status.ACTIVE);
        Referee referee1 = new Referee(UUID.randomUUID(), "Ivaylo", Country.BULGARIA, Status.ARCHIVED);
        Referee referee2 = new Referee(UUID.randomUUID(), "PeturFiliata", Country.BULGARIA, Status.ACTIVE);

        when(refereeRepository.findAll()).thenReturn(List.of(referee,referee1, referee2));

        List<Referee> refereeList = refereeService.getAllActiveReferees();

        assertThat(refereeList).isEqualTo(List.of(referee, referee2));
    }
    @Test
    void givenValidId_whenGettingRefereeById_shouldReturnReferee() {
        UUID refereeId = UUID.randomUUID();

        Referee referee = new Referee(refereeId, "Nikola Ant", Country.BULGARIA, Status.ACTIVE);
        when(refereeRepository.findById(any(UUID.class))).thenReturn(Optional.of(referee));

        Referee realReferee = refereeService.getRefereeById(refereeId);
        assertThat(realReferee).isEqualTo(referee);
    }

    @Test
    void givenInvalidId_whenGettingRefereeById_thenThrowException() {
        UUID refereeId = UUID.randomUUID();
        when(refereeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> refereeService.getRefereeById(refereeId));
    }
}
