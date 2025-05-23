package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.RestaurantDTO;
import com.api_food.Algaworks_Food.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    @PostMapping("/add")
    public ResponseEntity<RestaurantDTO> newRestaurant(@RequestBody RestaurantDTO restaurant){
        RestaurantDTO addRestaurant = restaurantService.newRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(addRestaurant);
    }
}
