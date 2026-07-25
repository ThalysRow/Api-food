package com.api_food.Algaworks_Food.domain.repository;

import com.api_food.Algaworks_Food.domain.model.ProductModel;
import com.api_food.Algaworks_Food.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    @Query("from ProductModel p where p.active = true and p.restaurant = :restaurant")
    List<ProductModel>findActiveProducts(RestaurantModel restaurant);

    @Query("from ProductModel p where p.restaurant.id = :restaurantId and p.id in :productIds")
    List<ProductModel>findByRestaurantIdProductsId(@Param("restaurantId") UUID restaurantId,
                                                   @Param("productIds") List<Integer> productIds );
}
