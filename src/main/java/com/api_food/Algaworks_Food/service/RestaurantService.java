package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.Mapper.RestaurantMapper;
import com.api_food.Algaworks_Food.dto.RestaurantDTO;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import com.api_food.Algaworks_Food.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
    private RestaurantMapper restaurantMapper;
    private RestaurantRepository restaurantRepository;

    public RestaurantDTO newRestaurant(RestaurantDTO restaurant){
        RestaurantModel newRestaurant = restaurantMapper.toEntity(restaurant);
        RestaurantModel restaurantSave = restaurantRepository.save(newRestaurant);
        return restaurantMapper.toDTO(restaurantSave);
    }
}
