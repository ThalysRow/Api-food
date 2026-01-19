package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.RestaurantOutput;
import com.api_food.Algaworks_Food.domain.model.RestaurantModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantOutput toOutput(RestaurantModel restaurant);
}
