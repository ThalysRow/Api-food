package com.api_food.Algaworks_Food.exception.custom;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(UUID id) {
        super(String.format("Could not find the user with the id: '%s'.", id));
    }
}
