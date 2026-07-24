package com.api_food.Algaworks_Food.domain.repository;

import com.api_food.Algaworks_Food.domain.model.PhotoProductModel;

import java.util.Optional;

public interface ProductRepositoryQueries {

    PhotoProductModel save(PhotoProductModel image);
    Optional<PhotoProductModel> findById(int productId);
    void remove(PhotoProductModel image);
}
