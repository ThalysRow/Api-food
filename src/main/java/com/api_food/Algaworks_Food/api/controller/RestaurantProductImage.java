package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.ImageUpdateInput;
import com.api_food.Algaworks_Food.api.dto.output.ImageUpdateOutput;
import com.api_food.Algaworks_Food.domain.service.PhotoProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("restaurants/{restaurantId}/products/{productId}/image")
@RequiredArgsConstructor
public class RestaurantProductImage {

    private final PhotoProductService photoProductService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ImageUpdateOutput updateImage(@PathVariable UUID restaurantId,
                                         @PathVariable int productId,
                                         @Valid ImageUpdateInput image){

        return photoProductService.save(image, restaurantId, productId);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeImage(@PathVariable UUID restaurantId, @PathVariable int productId){
        photoProductService.removeImage(restaurantId, productId);
    }
}
