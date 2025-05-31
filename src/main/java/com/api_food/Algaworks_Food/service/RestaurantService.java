package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.dto.list.KitchenListDTO;
import com.api_food.Algaworks_Food.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.exception.EntityNotFoundException;
import com.api_food.Algaworks_Food.mapper.KitchenMapper;
import com.api_food.Algaworks_Food.mapper.RestaurantMapper;
import com.api_food.Algaworks_Food.model.KitchenModel;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import com.api_food.Algaworks_Food.repository.RestaurantRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final KitchenService kitchenService;
    private final StringFormatter stringFormatter;
    private final KitchenMapper kitchenMapper;

    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository, KitchenService kitchenService, StringFormatter stringFormatter, KitchenMapper kitchenMapper) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.kitchenService = kitchenService;
        this.stringFormatter = stringFormatter;
        this.kitchenMapper = kitchenMapper;
    }

    public RestaurantCreateDTO newRestaurant(RestaurantCreateDTO restaurant){

        KitchenListDTO findKitchen = kitchenService.findKitchenById(restaurant.getKitchen().getId());
        KitchenModel saveKitchen = kitchenMapper.toModel(findKitchen);

        String formatedName = stringFormatter.stringFormated(restaurant.getName());

        RestaurantModel newRestaurant = restaurantMapper.toCreateModel(restaurant);
        newRestaurant.setName(formatedName);
        newRestaurant.setDeliveryFee(restaurant.getDeliveryFee());
        newRestaurant.setKitchen(saveKitchen);
        newRestaurant.setDateCreated(LocalDateTime.now());
        newRestaurant.setDateUpdated(LocalDateTime.now());

        RestaurantModel saveRestaurant = restaurantRepository.save(newRestaurant);
        return restaurantMapper.toCreateDTO(saveRestaurant);

    }

    public RestaurantListDTO findRestaurantById(UUID id){
        RestaurantModel restaurant = restaurantRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Restaurant not found"));
        return restaurantMapper.toListDTO(restaurant);
    }

    public List<RestaurantListDTO> listRestaurants(){
        return restaurantRepository.findAll().stream().map(restaurantMapper::toListDTO).toList();
    }

    public RestaurantUpdateDTO updateRestaurant(UUID id, RestaurantUpdateDTO restaurant){

        RestaurantListDTO findRestaurant = this.findRestaurantById(id);
        KitchenListDTO findKitchen = kitchenService.findKitchenById(restaurant.getKitchen().getId());

        RestaurantModel updateRestaurant = restaurantMapper.toUpdateModel(findRestaurant);
        KitchenModel updateKitchen = kitchenMapper.toModel(findKitchen);
        String nameFormated = stringFormatter.stringFormated(restaurant.getName());

        updateRestaurant.setName(nameFormated);
        updateRestaurant.setDeliveryFee(restaurant.getDeliveryFee());
        updateRestaurant.setKitchen(updateKitchen);
        updateRestaurant.setDateUpdated(LocalDateTime.now());

        RestaurantModel saveRestaurant = restaurantRepository.save(updateRestaurant);
        return restaurantMapper.toUpdateDTO(saveRestaurant);
        }

        public void deleteRestaurant(UUID id){
        RestaurantListDTO restaurant = this.findRestaurantById(id);
        restaurantRepository.deleteById(restaurant.getId());
        }


}
