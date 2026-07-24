package com.api_food.Algaworks_Food.domain.exception.custom;

public class PhotoNotFoundInProductException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public PhotoNotFoundInProductException(int productId) {
        super(String.format("There is no photo for product id: '%d'.", productId));
    }
}
