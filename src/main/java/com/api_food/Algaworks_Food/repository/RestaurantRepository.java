package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {
}
