package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.Mapper.RestaurantMapper;
import com.api_food.Algaworks_Food.dto.RestaurantDTO;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import com.api_food.Algaworks_Food.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    private RestaurantMapper restaurantMapper;
    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantDTO newRestaurant(RestaurantDTO restaurant){
        RestaurantModel newRestaurant = restaurantMapper.toEntity(restaurant);
        RestaurantModel restaurantSave = restaurantRepository.save(newRestaurant);
        return restaurantMapper.toDTO(restaurantSave);
    }

    public RestaurantDTO findRestaurantById(UUID id){
        RestaurantModel restaurant = restaurantRepository.findById(id).orElseThrow(()-> new RuntimeException("Restaurant not found"));
        return restaurantMapper.toDTO(restaurant);
    }

    public List<RestaurantDTO> listRestaurants(){
        return restaurantRepository.findAll().stream().map(restaurantMapper::toDTO).toList();
    }
}
