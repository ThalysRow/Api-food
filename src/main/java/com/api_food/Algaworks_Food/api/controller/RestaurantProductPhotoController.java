package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.PhotoUpdateInput;
import com.api_food.Algaworks_Food.api.dto.output.PhotoOutput;
import com.api_food.Algaworks_Food.domain.service.PhotoProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("restaurants/{restaurantId}/products/{productId}/photo")
@RequiredArgsConstructor
public class RestaurantProductPhotoController {

    private final PhotoProductService photoProductService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PhotoOutput updatePhoto(@PathVariable UUID restaurantId,
                                  @PathVariable int productId,
                                  @Valid PhotoUpdateInput photo){

        return photoProductService.save(photo, restaurantId, productId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePhoto(@PathVariable UUID restaurantId, @PathVariable int productId){
        photoProductService.removePhoto(restaurantId, productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PhotoOutput findPhotoById(@PathVariable UUID restaurantId, @PathVariable int productId){
        return photoProductService.findPhotoById(restaurantId, productId);
    }

    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> servePhotoFile(@PathVariable UUID restaurantId, @PathVariable int productId,
                                            @RequestHeader(name = "accept") String acceptHeader){
            PhotoOutput photo = photoProductService.findPhotoWithMediaValidation(restaurantId,productId, acceptHeader);

            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(photo.getUrl())).build();
    }

}
