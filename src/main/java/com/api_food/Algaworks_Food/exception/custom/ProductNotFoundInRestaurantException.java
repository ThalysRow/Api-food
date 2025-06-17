package com.api_food.Algaworks_Food.exception.custom;

public class ProductNotFoundInRestaurantException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public ProductNotFoundInRestaurantException(String message) {
        super(message);
    }

    public ProductNotFoundInRestaurantException(int productId, String restaurantName) {
        this(String.format("Product with id %d not found in restaurant %s", productId, restaurantName));
    }
}
