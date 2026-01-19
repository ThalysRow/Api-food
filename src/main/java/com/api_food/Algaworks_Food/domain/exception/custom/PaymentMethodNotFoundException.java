package com.api_food.Algaworks_Food.domain.exception.custom;

public class PaymentMethodNotFoundException extends EntityNotFoundException {

  private static final long serialVersionUID = 1L;

    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(int id){
      this(String.format("Could not find the payment method with id: '%d'.", id));
    }
}
