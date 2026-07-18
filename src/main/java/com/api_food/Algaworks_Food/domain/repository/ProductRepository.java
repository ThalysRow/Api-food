package com.api_food.Algaworks_Food.domain.repository;

import com.api_food.Algaworks_Food.domain.model.ProductModel;
import com.api_food.Algaworks_Food.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    @Query("from ProductModel p where p.active = true and p.restaurant = :restaurant")
    List<ProductModel>findActiveProducts(RestaurantModel restaurant);
}
