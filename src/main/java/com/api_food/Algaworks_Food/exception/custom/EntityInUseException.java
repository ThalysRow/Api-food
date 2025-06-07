package com.api_food.Algaworks_Food.exception.custom;

public class EntityInUseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityInUseException(String message) {
        super(message);
    }
}
