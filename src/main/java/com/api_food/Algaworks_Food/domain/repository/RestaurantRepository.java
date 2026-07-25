package com.api_food.Algaworks_Food.domain.repository;

import com.api_food.Algaworks_Food.domain.model.RestaurantModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {


    @Modifying
    @Query("update RestaurantModel r set r.active = true where r.id in: ids")
    void activateAll(@Param("ids") List<UUID> ids);

    @Modifying
    @Query("update RestaurantModel r set r.active = false where r.id in: ids")
    void deactivateAll(@Param("ids") List<UUID> ids);

    @EntityGraph(attributePaths = {"kitchen", "address", "address.city", "address.city.state"})
    List<RestaurantModel> findAll();
}
