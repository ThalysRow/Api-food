package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity<RestaurantCreateDTO> newRestaurant(@Valid @RequestBody RestaurantCreateDTO restaurant){
        RestaurantCreateDTO newRestaurant = restaurantService.newRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRestaurant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantListDTO> findRestaurant(@PathVariable UUID id){
        RestaurantListDTO restaurant = restaurantService.findRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantListDTO>> lisRestaurants(){
        List<RestaurantListDTO> restaurants = restaurantService.listRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantUpdateDTO> updateRestaurant(@PathVariable UUID id, @Valid @RequestBody RestaurantUpdateDTO restaurant){
        RestaurantUpdateDTO updateRestaurant = restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(updateRestaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable UUID id){
        restaurantService.deleteRestaurant(id);
    }
}
