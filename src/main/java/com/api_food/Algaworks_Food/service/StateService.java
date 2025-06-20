package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.StateCreateDTO;
import com.api_food.Algaworks_Food.dto.list.StateListDTO;
import com.api_food.Algaworks_Food.dto.update.StateUpdateDTO;
import com.api_food.Algaworks_Food.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.exception.custom.StateNotFoundException;
import com.api_food.Algaworks_Food.mapper.StateMapper;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.StateRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public StateCreateDTO createNewState(StateCreateDTO state){
        String nameFormated = stringFormatter.stringFormated(state.getName());
        state.setName(nameFormated);

        StateModel newState = stateMapper.toCreateModel(state);
        StateModel stateSave = stateRepository.save(newState);
        return stateMapper.toCreateDTO(stateSave);
    }

    public StateListDTO findStateById(int id){
        StateModel stateFinded = stateRepository.findById(id).orElseThrow(()-> new StateNotFoundException(id));
        return stateMapper.toCreateListDTO(stateFinded);
    }

    @Transactional
    public void delState(int id){
        StateModel stateFinded = stateRepository.findById(id).orElseThrow(()-> new StateNotFoundException(id));

        if(stateFinded.getCities() != null && !stateFinded.getCities().isEmpty()){
            throw new EntityInUseException("state", stateFinded.getId(), "cities");
        }
        stateRepository.deleteById(stateFinded.getId());
    }

    public List<StateListDTO> listStates(){
        return stateRepository.findAll().stream().map(stateMapper::toCreateListDTO).toList();
    }

    @Transactional
    public StateUpdateDTO updateState(int id, StateUpdateDTO state){
        StateListDTO stateFinded = this.findStateById(id);
        String nameFormated = stringFormatter.stringFormated(state.getName());
        stateFinded.setName(nameFormated);

        StateModel newState = stateMapper.toUpdateModel(stateFinded);
        StateModel saveState = stateRepository.save(newState);
        return stateMapper.toUpdateDTO(saveState);
    }
}
