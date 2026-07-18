package com.api_food.Algaworks_Food.domain.repository;

import com.api_food.Algaworks_Food.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {

    @EntityGraph(attributePaths = {"kitchen", "address", "address.city", "address.city.state"})
    List<RestaurantModel> findAll();
}
