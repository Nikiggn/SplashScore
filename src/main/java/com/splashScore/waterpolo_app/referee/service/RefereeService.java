package com.splashScore.waterpolo_app.referee.service;

import com.splashScore.waterpolo_app.referee.model.Referee;
import com.splashScore.waterpolo_app.referee.model.Status;
import com.splashScore.waterpolo_app.referee.repository.RefereeRepository;
import com.splashScore.waterpolo_app.web.dto.AddRefereeRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RefereeService {
    private final RefereeRepository refereeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RefereeService(RefereeRepository refereeRepository, ModelMapper modelMapper) {
        this.refereeRepository = refereeRepository;
        this.modelMapper = modelMapper;
    }

    public List<Referee> getAllReferees() {
            return refereeRepository.findAll().stream().sorted(Comparator.comparing(Referee::getStatus)).collect(Collectors.toList());
    }

    public void saveNewReferee(AddRefereeRequest addRefereeRequest) {
        Referee referee = modelMapper.map(addRefereeRequest, Referee.class);
        referee.setStatus(Status.ACTIVE);

        refereeRepository.save(referee);
    }

    @Transactional
    public void changeRefereeStatus(UUID id) {
        Referee referee = refereeRepository.findById(id).orElseThrow(() -> new RuntimeException("Referee not found with id: " + id));

        if (referee.getStatus() == Status.ACTIVE) {
            referee.setStatus(Status.ARCHIVED);
        }else {
            referee.setStatus(Status.ACTIVE);
        }
    }
}
