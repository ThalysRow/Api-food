package com.api_food.Algaworks_Food.domain.exception.custom;

public class PaymentMethodAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PaymentMethodAlreadyExistsException(String name){
        super(String.format("The payment method name: '%s' already exist.", name));
    }
}
