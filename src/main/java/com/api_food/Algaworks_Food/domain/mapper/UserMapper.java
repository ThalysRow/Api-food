package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.UserCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.UserListDTO;
import com.api_food.Algaworks_Food.api.dto.update.UserUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserModel toCreateModel(UserCreateDTO userCreateDTO);
    UserCreateDTO toCreateDTO(UserModel userModel);
    UserListDTO toListDTO(UserModel userModel);
    UserModel toUpdateModel(UserUpdateDTO userUpdateDTO);
    UserUpdateDTO toUpdateDTO(UserModel userModel);
}
