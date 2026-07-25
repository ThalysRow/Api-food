package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.PhotoUpdateInput;
import com.api_food.Algaworks_Food.api.dto.output.PhotoUpdateOutput;
import com.api_food.Algaworks_Food.domain.service.PhotoProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("restaurants/{restaurantId}/products/{productId}/photo")
@RequiredArgsConstructor
public class RestaurantProductPhotoController {

    private final PhotoProductService photoProductService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PhotoUpdateOutput updatePhoto(@PathVariable UUID restaurantId,
                                         @PathVariable int productId,
                                         @Valid PhotoUpdateInput photo){

        return photoProductService.save(photo, restaurantId, productId);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePhoto(@PathVariable UUID restaurantId, @PathVariable int productId){
        photoProductService.removeImage(restaurantId, productId);
    }
}
