package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.list.PermissionListDTO;
import com.api_food.Algaworks_Food.model.PermissionModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionListDTO toListDTO(PermissionModel permissionModel);
}
