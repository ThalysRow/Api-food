package com.api_food.Algaworks_Food.repository;
import com.api_food.Algaworks_Food.model.CityModel;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityModel, Id> {
}
