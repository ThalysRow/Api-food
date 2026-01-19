package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.KitchenCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.KitchenListDTO;
import com.api_food.Algaworks_Food.api.dto.update.KitchenUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.KitchenModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KitchenMapper {
    KitchenCreateDTO toCreateDTO(KitchenModel kitchenModel);
    KitchenModel toCreateModel(KitchenCreateDTO kitchenCreateDTO);
    KitchenListDTO toCreateListDTO(KitchenModel kitchenModel);
    KitchenModel toModel(KitchenListDTO kitchenListDTO);
    KitchenUpdateDTO toUpdateDTO(KitchenModel kitchenModel);
}
