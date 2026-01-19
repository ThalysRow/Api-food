package com.api_food.Algaworks_Food.domain.exception.custom;

public class GroupNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public GroupNotFoundException(int id) {
        super(String.format("Could not find the group with the id: '%d'.", id));
    }
}
