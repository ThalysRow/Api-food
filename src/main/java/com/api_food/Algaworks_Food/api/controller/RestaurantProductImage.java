package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.ImageUpdateInput;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("restaurants/{restaurantId}/products/{productId}/image")
public class RestaurantProductImage {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateImage(@PathVariable UUID restauranrId,
                            @PathVariable Long productId,
                            @Valid ImageUpdateInput image){

    }
}
