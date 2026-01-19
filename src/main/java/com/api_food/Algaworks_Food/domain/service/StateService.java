package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.StateInput;
import com.api_food.Algaworks_Food.api.dto.output.StateOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.exception.custom.StateNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.StateMapper;
import com.api_food.Algaworks_Food.domain.model.StateModel;
import com.api_food.Algaworks_Food.domain.repository.StateRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;

    @Transactional
    public StateOutput createNewState(StateInput input){

        String name = Formatter.string(input.getName());

        StateModel state = StateModel.addState(name);

        return stateMapper.toOutput(stateRepository.saveAndFlush(state));
    }

    public StateOutput findStateById(int id){
        return stateMapper.toOutput(this.returnStateModel(id));
    }

    @Transactional
    public void delState(int id){
        StateModel stateFound = this.returnStateModel(id);

        if(stateFound.getCities() != null && !stateFound.getCities().isEmpty()){
            throw new EntityInUseException("state", stateFound.getId(), "cities");
        }
        stateRepository.deleteById(stateFound.getId());
    }

    public List<StateOutput> listStates(){
        return stateRepository.findAll().stream().map(stateMapper::toOutput).toList();
    }

    @Transactional
    public StateOutput updateState(int id, StateInput input){
        StateModel foundState = this.returnStateModel(id);

        String name = Formatter.string(input.getName());

        foundState.setName(name);

        return stateMapper.toOutput(stateRepository.saveAndFlush(foundState));
    }

    public StateModel returnStateModel(int id){
        return stateRepository.findById(id).orElseThrow(()-> new StateNotFoundException(id));
    }

    public StateModel verifyStateField(int id){
        return  stateRepository.findById(id)
                .orElseThrow(() -> new BusinessException("state", id));
    }
}
