package com.api_food.Algaworks_Food.domain.repository;
import com.api_food.Algaworks_Food.domain.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityModel, Integer> {
}
