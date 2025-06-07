package com.api_food.Algaworks_Food.exception.custom;

import java.util.UUID;

public class RestaurantNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public RestaurantNotFoundException(UUID restaurant_id) {
        super(String.format("Could not found the restaurant with the id: '%s'.", restaurant_id));
    }
}
