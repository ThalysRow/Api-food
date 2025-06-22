package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
}
