package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findUserByEmail(String email);
}
