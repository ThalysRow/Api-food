package com.api_food.Algaworks_Food.exception.custom;

public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }
}
