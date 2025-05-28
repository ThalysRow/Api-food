package com.api_food.Algaworks_Food.Mapper;

import com.api_food.Algaworks_Food.dto.RestaurantDTO;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDTO toDTO(RestaurantModel restaurantModel);
    RestaurantModel toEntity(RestaurantDTO restaurantDTO);
}
