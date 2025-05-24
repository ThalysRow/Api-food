package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.Mapper.StateMapper;
import com.api_food.Algaworks_Food.dto.StateDTO;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.StateRepository;
import org.springframework.stereotype.Service;


@Service
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;

    public StateService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    public StateDTO createNewState(StateDTO state){
        StateModel newState = stateMapper.toEntity(state);
        StateModel stateSave = stateRepository.save(newState);
        return stateMapper.toDTO(stateSave);
    }

    public StateDTO findStateById(int id){
        StateModel findedState = stateRepository.findById(id).orElseThrow(()-> new RuntimeException("State not found"));
        return stateMapper.toDTO(findedState);
    }

    public void delState(int id){
        StateModel state = stateRepository.findById(id).orElseThrow(()-> new RuntimeException("State not found"));
        stateRepository.deleteById(state.getId());
    }
}
