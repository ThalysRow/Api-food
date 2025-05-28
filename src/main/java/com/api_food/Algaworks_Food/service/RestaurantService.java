package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.mapper.RestaurantMapper;
import com.api_food.Algaworks_Food.dto.RestaurantDTO;
import com.api_food.Algaworks_Food.model.KitchenModel;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import com.api_food.Algaworks_Food.repository.KitchenRepository;
import com.api_food.Algaworks_Food.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final KitchenRepository kitchenRepository;

    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository, KitchenRepository kitchenRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.kitchenRepository = kitchenRepository;
    }

    public RestaurantDTO newRestaurant(RestaurantDTO restaurant){
        UUID kitchenId = restaurant.getKitchen().getId();
        KitchenModel kitchen = kitchenRepository.findById(kitchenId).orElseThrow(()-> new RuntimeException("Kitchen not found"));

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

    public RestaurantDTO updateRestaurant(UUID id,RestaurantDTO restaurant){

       RestaurantModel restaurantFinded = restaurantRepository.findById(id).orElseThrow(()-> new RuntimeException("Restaurant not found"));
       UUID kitchenId = restaurant.getKitchen().getId();
       KitchenModel kitchenFinded = kitchenRepository.findById(kitchenId).orElseThrow(()-> new RuntimeException("Kitchen not found"));

       RestaurantModel restaurantModel = restaurantMapper.toEntity(restaurant);
        restaurantModel.setId(id);
        restaurantModel.setKitchen(restaurantModel.getKitchen());

        RestaurantModel restaurantUpdate = restaurantRepository.save(restaurantModel);
        return restaurantMapper.toDTO(restaurantUpdate);

        }

}
