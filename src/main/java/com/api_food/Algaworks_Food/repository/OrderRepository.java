package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
    @Query("from OrderModel o join fetch o.user join fetch o.restaurant r join fetch r.kitchen")
    List<OrderModel> findAll();
}
