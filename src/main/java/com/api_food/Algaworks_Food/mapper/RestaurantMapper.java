package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantModel toCreateModel(RestaurantCreateDTO restaurantCreateDTO);
    RestaurantCreateDTO toCreateDTO(RestaurantModel restaurantModel);
    RestaurantListDTO toListDTO(RestaurantModel restaurantModel);
    RestaurantModel toUpdateModel(RestaurantListDTO restaurantListDTO);
    RestaurantUpdateDTO toUpdateDTO(RestaurantModel restaurantModel);

}
