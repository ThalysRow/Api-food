package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.Mapper.StateMapper;
import com.api_food.Algaworks_Food.dto.StateDTO;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.StateRepository;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    private StateRepository stateRepository;
    private StateMapper stateMapper;

    public StateService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    public StateDTO createNewState(StateDTO state){
        StateModel newState = stateMapper.toEntity(state);
        StateModel stateSave = stateRepository.save(newState);
        return stateMapper.toDTO(stateSave);
    }
}
