package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.GroupOutput;
import com.api_food.Algaworks_Food.domain.model.GroupModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupOutput toOutput(GroupModel group);
}
