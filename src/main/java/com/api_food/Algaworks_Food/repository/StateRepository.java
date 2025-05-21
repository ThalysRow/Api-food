package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.StateModel;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateModel, Id> {
}
