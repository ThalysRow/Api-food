package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.api.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.RestaurantModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantModel toCreateModel(RestaurantCreateDTO restaurantCreateDTO);
    RestaurantCreateDTO toCreateDTO(RestaurantModel restaurantModel);
    RestaurantListDTO toListDTO(RestaurantModel restaurantModel);
    RestaurantModel toUpdateModel(RestaurantUpdateDTO restaurantUpdateDTO);
    RestaurantUpdateDTO toUpdateDTO(RestaurantModel restaurantModel);

}
