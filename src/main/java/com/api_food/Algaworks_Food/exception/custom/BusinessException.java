package com.api_food.Algaworks_Food.exception.custom;

import java.util.UUID;

public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String name, int id){
        this(String.format("The resource '%s' with id '%d' was not found.",name, id));
    }

    public BusinessException(String name, UUID id){
        this(String.format("The resource '%s' with id '%s' was not found.", name, id));
    }
}
