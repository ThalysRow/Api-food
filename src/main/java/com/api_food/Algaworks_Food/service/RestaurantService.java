package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.exception.custom.RestaurantNotFoundException;
import com.api_food.Algaworks_Food.mapper.RestaurantMapper;
import com.api_food.Algaworks_Food.model.KitchenModel;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import com.api_food.Algaworks_Food.repository.KitchenRepository;
import com.api_food.Algaworks_Food.repository.RestaurantRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final StringFormatter stringFormatter;
    private final KitchenRepository kitchenRepository;

    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository, StringFormatter stringFormatter, KitchenRepository kitchenRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.stringFormatter = stringFormatter;
        this.kitchenRepository = kitchenRepository;
    }

    @Transactional
    public RestaurantCreateDTO newRestaurant(RestaurantCreateDTO restaurant){

        KitchenModel findKitchen = kitchenRepository.findById(restaurant.getKitchen().getId())
                .orElseThrow(()-> new BusinessException("kitchen", restaurant.getKitchen().getId()));

        String formatedName = stringFormatter.stringFormated(restaurant.getName());

        RestaurantModel newRestaurant = restaurantMapper.toCreateModel(restaurant);
        newRestaurant.setName(formatedName);
        newRestaurant.setDeliveryFee(restaurant.getDeliveryFee());
        newRestaurant.setKitchen(findKitchen);
        newRestaurant.setDateCreated(LocalDateTime.now());
        newRestaurant.setDateUpdated(LocalDateTime.now());

        RestaurantModel saveRestaurant = restaurantRepository.save(newRestaurant);
        return restaurantMapper.toCreateDTO(saveRestaurant);

    }

    public RestaurantListDTO findRestaurantById(UUID id){
        RestaurantModel restaurant = restaurantRepository.findById(id).orElseThrow(()-> new RestaurantNotFoundException(id));
        return restaurantMapper.toListDTO(restaurant);
    }

    public List<RestaurantListDTO> listRestaurants(){
        return restaurantRepository.findAll().stream().map(restaurantMapper::toListDTO).toList();
    }

    @Transactional
    public RestaurantUpdateDTO updateRestaurant(UUID id, RestaurantUpdateDTO restaurant){

        RestaurantListDTO findRestaurant = this.findRestaurantById(id);
        KitchenModel findKitchen = kitchenRepository.findById(restaurant.getKitchen().getId())
                .orElseThrow(()-> new BusinessException("kitchen", restaurant.getKitchen().getId()));

        RestaurantModel updateRestaurant = restaurantMapper.toUpdateModel(findRestaurant);
        String nameFormated = stringFormatter.stringFormated(restaurant.getName());

        updateRestaurant.setName(nameFormated);
        updateRestaurant.setDeliveryFee(restaurant.getDeliveryFee());
        updateRestaurant.setKitchen(findKitchen);
        updateRestaurant.setDateUpdated(LocalDateTime.now());

        RestaurantModel saveRestaurant = restaurantRepository.save(updateRestaurant);
        return restaurantMapper.toUpdateDTO(saveRestaurant);
        }

        @Transactional
        public void deleteRestaurant(UUID id){
        RestaurantListDTO restaurant = this.findRestaurantById(id);
        restaurantRepository.deleteById(restaurant.getId());
        }


}
