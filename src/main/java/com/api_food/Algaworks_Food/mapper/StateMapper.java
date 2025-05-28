package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.StateDTO;
import com.api_food.Algaworks_Food.model.StateModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateDTO toDTO(StateModel stateModel);
    StateModel toEntity(StateDTO stateDTO);
}
