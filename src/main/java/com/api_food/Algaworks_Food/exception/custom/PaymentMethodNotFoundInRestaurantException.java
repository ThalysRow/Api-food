package com.api_food.Algaworks_Food.exception.custom;

public class PaymentMethodNotFoundInRestaurantException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public PaymentMethodNotFoundInRestaurantException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundInRestaurantException(int id, String restaurantName) {
        this(String.format("The payment method with id %d does not exist in restaurant: %s.", id, restaurantName));
    }

}
