package com.api_food.Algaworks_Food.exception.custom;

public class GroupNameAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public GroupNameAlreadyExistsException(String groupName) {
        super(String.format("The group name: '%s' already exit.", groupName));
    }
}
