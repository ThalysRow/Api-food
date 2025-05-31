package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.StateCreateDTO;
import com.api_food.Algaworks_Food.dto.list.StateListDTO;
import com.api_food.Algaworks_Food.dto.update.StateUpdateDTO;
import com.api_food.Algaworks_Food.model.StateModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateCreateDTO toCreateDTO(StateModel stateModel);
    StateModel toCreateModel(StateCreateDTO stateCreateDTO);
    StateListDTO toCreateListDTO(StateModel stateModel);
    StateModel toUpdateModel(StateListDTO stateListDTO);
    StateUpdateDTO toUpdateDTO(StateModel stateModel);
}
