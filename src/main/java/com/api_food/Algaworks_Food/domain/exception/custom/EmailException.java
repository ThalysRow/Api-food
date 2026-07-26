package com.api_food.Algaworks_Food.domain.exception.custom;

public class EmailException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(){
        this("Failed to send email");
    }
}
