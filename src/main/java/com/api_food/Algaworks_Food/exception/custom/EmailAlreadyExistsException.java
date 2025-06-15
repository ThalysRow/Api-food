package com.api_food.Algaworks_Food.exception.custom;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public EmailAlreadyExistsException(String email) {
        super(String.format("An account with the email address: '%s' already exists.", email));
    }
}
