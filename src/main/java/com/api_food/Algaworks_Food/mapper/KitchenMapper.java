package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.KitchenCreateDTO;
import com.api_food.Algaworks_Food.dto.list.KitchenListDTO;
import com.api_food.Algaworks_Food.dto.update.KitchenUpdateDTO;
import com.api_food.Algaworks_Food.model.KitchenModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KitchenMapper {
    KitchenCreateDTO toCreateDTO(KitchenModel kitchenModel);
    KitchenModel toCreateModel(KitchenCreateDTO kitchenCreateDTO);
    KitchenListDTO toCreateListDTO(KitchenModel kitchenModel);
    KitchenModel toModel(KitchenListDTO kitchenListDTO);
    KitchenUpdateDTO toUpdateDTO(KitchenModel kitchenModel);
}
