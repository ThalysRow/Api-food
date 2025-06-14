package com.api_food.Algaworks_Food.repository;

import com.api_food.Algaworks_Food.model.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupModel, Integer> {
    Optional<GroupModel> findGroupByName(String name);
}
