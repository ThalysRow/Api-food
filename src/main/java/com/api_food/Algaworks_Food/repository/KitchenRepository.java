package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.KitchenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KitchenRepository extends JpaRepository<KitchenModel, UUID> {
}
