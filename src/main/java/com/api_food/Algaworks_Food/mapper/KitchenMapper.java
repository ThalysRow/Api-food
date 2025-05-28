package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.KitchenDTO;
import com.api_food.Algaworks_Food.model.KitchenModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KitchenMapper {
    KitchenDTO toDTO(KitchenModel kitchenModel);
    KitchenModel toEntity(KitchenDTO kitchenDTO);
}
