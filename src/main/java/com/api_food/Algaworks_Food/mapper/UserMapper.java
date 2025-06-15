package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.UserCreateDTO;
import com.api_food.Algaworks_Food.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel toCreateModel(UserCreateDTO userCreateDTO);
    UserCreateDTO toCreateDTO(UserModel userModel);
}
