package com.api_food.Algaworks_Food.exception.custom;

public  abstract class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
