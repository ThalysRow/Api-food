package com.api_food.Algaworks_Food.infra.repository;

import com.api_food.Algaworks_Food.domain.model.PhotoProductModel;
import com.api_food.Algaworks_Food.domain.repository.ProductRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

   @PersistenceContext
   private EntityManager manager;

    @Override
    @Transactional
    public PhotoProductModel save(PhotoProductModel image) {

        return manager.merge(image);

    }

    @Override
    public Optional<PhotoProductModel> findById(int productId) {
        return Optional.ofNullable(manager.find(PhotoProductModel.class, productId));
    }

    @Override
    public void remove(PhotoProductModel image) {
        manager.remove(image);
    }
}
