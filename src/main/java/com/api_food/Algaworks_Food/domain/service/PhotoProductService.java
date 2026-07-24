package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.ImageUpdateInput;
import com.api_food.Algaworks_Food.api.dto.output.ImageUpdateOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.PhotoNotFoundInProductException;
import com.api_food.Algaworks_Food.domain.mapper.PhotoProductMapper;
import com.api_food.Algaworks_Food.domain.model.PhotoProductModel;
import com.api_food.Algaworks_Food.domain.model.ProductModel;
import com.api_food.Algaworks_Food.domain.repository.ProductRepositoryQueries;
import com.api_food.Algaworks_Food.infra.storage.PhotoStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoProductService {

    private final ProductService productService;

    @Qualifier("productRepositoryImpl")
    private final ProductRepositoryQueries productRepositoryQueries;
    private final PhotoProductMapper photoProductMapper;
    private final PhotoStorage photoStorage;


    @Transactional
    public ImageUpdateOutput save(ImageUpdateInput input, UUID restaurantId, int productId){

        ProductModel product = productService.findProductModelInRestaurant(restaurantId, productId);

        String fileName = photoStorage.save(input.getImage(), productId);

        Optional<PhotoProductModel> findedPhoto = productRepositoryQueries.findById(productId);

        PhotoProductModel photo;

        if(findedPhoto.isPresent()){

            photo = findedPhoto.get();

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

        ImageUpdateOutput output = photoProductMapper.toDto(photo);
        output.setUrl(photoStorage.getUrl(photo.getFileName()));

        return output;
    }

    @Transactional
    public void removeImage(UUID restaurantId, int productId){

        productService.findProductModelInRestaurant(restaurantId, productId);

        PhotoProductModel image = productRepositoryQueries.findById(productId)
                .orElseThrow(()-> new PhotoNotFoundInProductException(productId));

        photoStorage.remove(image.getFileName());
        productRepositoryQueries.remove(image);

    }

}
