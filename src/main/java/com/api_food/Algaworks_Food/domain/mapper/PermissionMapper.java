package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.list.PermissionListDTO;
import com.api_food.Algaworks_Food.domain.model.PermissionModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionListDTO toListDTO(PermissionModel permissionModel);
}
