package com.api_food.Algaworks_Food.domain.repository;

import com.api_food.Algaworks_Food.domain.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionModel,Integer> {
}
