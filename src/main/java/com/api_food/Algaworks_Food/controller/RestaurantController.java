package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.RestaurantDTO;
import com.api_food.Algaworks_Food.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> newRestaurant(@RequestBody RestaurantDTO restaurant){
        try {
            RestaurantDTO addRestaurant = restaurantService.newRestaurant(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(addRestaurant);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findRestaurant(@PathVariable UUID id){
        try {
            RestaurantDTO restaurant = restaurantService.findRestaurantById(id);
            return ResponseEntity.ok(restaurant);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDTO>> lisRestaurants(){
        List<RestaurantDTO> restaurants = restaurantService.listRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRestaurant(@PathVariable UUID id, @RequestBody RestaurantDTO restaurant){
        try {
            RestaurantDTO restaurantUpdate = restaurantService.updateRestaurant(id, restaurant);
            return ResponseEntity.status(HttpStatus.OK).body(restaurantUpdate);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
