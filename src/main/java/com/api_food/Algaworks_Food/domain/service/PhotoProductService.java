package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.PhotoUpdateInput;
import com.api_food.Algaworks_Food.api.dto.output.PhotoOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.HttpMediaTypeNotAcceptableException;
import com.api_food.Algaworks_Food.domain.exception.custom.PhotoNotFoundInProductException;
import com.api_food.Algaworks_Food.domain.mapper.PhotoProductMapper;
import com.api_food.Algaworks_Food.domain.model.PhotoProductModel;
import com.api_food.Algaworks_Food.domain.model.ProductModel;
import com.api_food.Algaworks_Food.domain.repository.ProductRepositoryQueries;
import com.api_food.Algaworks_Food.infra.storage.PhotoStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoProductService {

    private final ProductService productService;
    private final ProductRepositoryQueries productRepositoryQueries;
    private final PhotoProductMapper photoProductMapper;
    private final PhotoStorage photoStorage;

    public PhotoOutput findPhotoById(UUID restaurantId, int productId){
        productService.findProductModelInRestaurant(restaurantId, productId);

        PhotoProductModel photo = this.returnPhotoProductModel(productId);
        PhotoOutput output = photoProductMapper.toOutput(photo);
        output.setUrl(photoStorage.getUrl(photo.getFileName()));

        return output;
    }

    public PhotoOutput findPhotoWithMediaValidation(UUID restaurantId, int productId, String acceptHeader){

        PhotoOutput photo = this.findPhotoById(restaurantId, productId);

        MediaType mediaTypePhoto = MediaType.parseMediaType(photo.getContentType());
        List<MediaType> mediaTypesAccept = MediaType.parseMediaTypes(acceptHeader);

        boolean isCompatible = mediaTypesAccept.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypePhoto));

        if (!isCompatible){
            throw new HttpMediaTypeNotAcceptableException(photo.getContentType());
        }

        return photo;

    }

    private PhotoProductModel returnPhotoProductModel(int productId){
        return productRepositoryQueries.findById(productId)
                .orElseThrow(()-> new PhotoNotFoundInProductException(productId));
    }

    @Transactional
    public PhotoOutput save(PhotoUpdateInput input, UUID restaurantId, int productId){

        ProductModel product = productService.findProductModelInRestaurant(restaurantId, productId);

        String fileName = photoStorage.save(input.getImage(), productId);

        Optional<PhotoProductModel> foundPhoto = productRepositoryQueries.findById(productId);

        PhotoProductModel photo;

        if(foundPhoto.isPresent()){

            photo = foundPhoto.get();

            if(!photo.getFileName().equals(fileName)){
                photoStorage.remove(photo.getFileName());
            }

            photo.update(fileName, input.getDescription(), input.getImage().getContentType(),
                    (int) input.getImage().getSize());
        } else {
            photo = PhotoProductModel.create(product, fileName, input.getDescription(), input.getImage().getContentType(),
                    (int) input.getImage().getSize());
        }

        photo = productRepositoryQueries.save(photo);

        PhotoOutput output = photoProductMapper.toOutput(photo);
        output.setUrl(photoStorage.getUrl(photo.getFileName()));

        return output;
    }

    @Transactional
    public void removePhoto(UUID restaurantId, int productId){

        productService.findProductModelInRestaurant(restaurantId, productId);

        PhotoProductModel photo = this.returnPhotoProductModel(productId);

        photoStorage.remove(photo.getFileName());
        productRepositoryQueries.remove(photo);

    }

}
