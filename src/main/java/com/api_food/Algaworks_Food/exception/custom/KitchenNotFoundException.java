package com.api_food.Algaworks_Food.exception.custom;

import java.util.UUID;

public class KitchenNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public KitchenNotFoundException(UUID kitchen_id) {
        super(String.format("Could not found the kitchen with the id: '%s'.", kitchen_id));
    }
}
