package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.create.StateCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.StateListDTO;
import com.api_food.Algaworks_Food.api.dto.update.StateUpdateDTO;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.exception.custom.StateNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.StateMapper;
import com.api_food.Algaworks_Food.domain.model.StateModel;
import com.api_food.Algaworks_Food.domain.repository.StateRepository;
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
        StateModel stateFound = this.returnStateModel(id);
        return stateMapper.toCreateListDTO(stateFound);
    }

    @Transactional
    public void delState(int id){
        StateModel stateFound = this.returnStateModel(id);

        if(stateFound.getCities() != null && !stateFound.getCities().isEmpty()){
            throw new EntityInUseException("state", stateFound.getId(), "cities");
        }
        stateRepository.deleteById(stateFound.getId());
    }

    public List<StateListDTO> listStates(){
        return stateRepository.findAll().stream().map(stateMapper::toCreateListDTO).toList();
    }

    @Transactional
    public StateUpdateDTO updateState(int id, StateUpdateDTO state){
        StateModel stateFound = this.returnStateModel(id);
        String nameFormated = stringFormatter.stringFormated(state.getName());
        stateFound.setName(nameFormated);

        StateModel saveState = stateRepository.save(stateFound);
        return stateMapper.toUpdateDTO(saveState);
    }

    public StateModel returnStateModel(int id){
        return stateRepository.findById(id).orElseThrow(()-> new StateNotFoundException(id));
    }

    public StateModel verifyStateField(int id){
        return  stateRepository.findById(id)
                .orElseThrow(() -> new BusinessException("state", id));
    }
}
