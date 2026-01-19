package com.api_food.Algaworks_Food.domain.exception.custom;

public class InvalidCurrentPasswordException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InvalidCurrentPasswordException() {
        super("Current password is wrong");
    }
}
