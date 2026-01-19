package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.GroupCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.GroupListDTO;
import com.api_food.Algaworks_Food.api.dto.update.GroupUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.GroupModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupModel toCreateModel(GroupCreateDTO groupCreateDTO);
    GroupCreateDTO toCreateDTO(GroupModel groupModel);
    GroupListDTO toListDTO(GroupModel groupModel);
    GroupModel toUpdateModel(GroupListDTO groupListDTO);
    GroupUpdateDTO toUpdateDTO(GroupModel groupModel);
}
