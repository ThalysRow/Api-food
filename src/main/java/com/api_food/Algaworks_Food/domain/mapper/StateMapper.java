package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.StateOutput;
import com.api_food.Algaworks_Food.domain.model.StateModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper {
    StateOutput toOutput(StateModel stateModel);
}
