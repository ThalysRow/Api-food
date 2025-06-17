package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
}
