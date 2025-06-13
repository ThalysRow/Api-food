package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.PaymentMethodModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodModel, Integer> {
    Optional<PaymentMethodModel> findByName(String name);
}
