package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.StateCreateDTO;
import com.api_food.Algaworks_Food.dto.list.StateListDTO;
import com.api_food.Algaworks_Food.dto.update.StateUpdateDTO;
import com.api_food.Algaworks_Food.exception.EntityInUseException;
import com.api_food.Algaworks_Food.exception.EntityNotFoundException;
import com.api_food.Algaworks_Food.mapper.StateMapper;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.StateRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private final StringFormatter stringFormatter;

    public StateService(StateRepository stateRepository, StateMapper stateMapper, StringFormatter stringFormatter) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
        this.stringFormatter = stringFormatter;
    }

    public StateCreateDTO createNewState(StateCreateDTO state){
        String nameFormated = stringFormatter.stringFormated(state.getName());
        state.setName(nameFormated);

        StateModel newState = stateMapper.toCreateModel(state);
        StateModel stateSave = stateRepository.save(newState);
        return stateMapper.toCreateDTO(stateSave);
    }

    public StateListDTO findStateById(int id){
        StateModel stateFinded = stateRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("State not found"));
        return stateMapper.toCreateListDTO(stateFinded);
    }

    public void delState(int id){
        StateModel stateFinded = stateRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("State not found"));

        if(stateFinded.getCities() != null && !stateFinded.getCities().isEmpty()){
            throw new EntityInUseException("State cannot be deleted, it is in use by cities.");
        }
        stateRepository.deleteById(stateFinded.getId());
    }

    public List<StateListDTO> listStates(){
        return stateRepository.findAll().stream().map(stateMapper::toCreateListDTO).toList();
    }

    public StateUpdateDTO updateState(int id, StateUpdateDTO state){
        StateListDTO stateFinded = this.findStateById(id);
        String nameFormated = stringFormatter.stringFormated(state.getName());
        stateFinded.setName(nameFormated);

        StateModel newState = stateMapper.toUpdateModel(stateFinded);
        StateModel saveState = stateRepository.save(newState);
        return stateMapper.toUpdateDTO(saveState);
    }
}
