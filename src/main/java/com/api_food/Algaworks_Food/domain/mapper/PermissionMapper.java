package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.PermissionsOutput;
import com.api_food.Algaworks_Food.domain.model.PermissionModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionsOutput toOutPut(PermissionModel permissionModel);
}
