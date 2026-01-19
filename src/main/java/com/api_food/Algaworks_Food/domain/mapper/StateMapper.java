package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.StateCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.StateListDTO;
import com.api_food.Algaworks_Food.api.dto.update.StateUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.StateModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateCreateDTO toCreateDTO(StateModel stateModel);
    StateModel toCreateModel(StateCreateDTO stateCreateDTO);
    StateListDTO toCreateListDTO(StateModel stateModel);
    StateModel toUpdateModel(StateListDTO stateListDTO);
    StateUpdateDTO toUpdateDTO(StateModel stateModel);
}
