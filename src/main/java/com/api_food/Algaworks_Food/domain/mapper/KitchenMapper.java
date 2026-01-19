package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.KitchenOutput;
import com.api_food.Algaworks_Food.domain.model.KitchenModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KitchenMapper {
    KitchenOutput toOutput(KitchenModel kitchenModel);
}
