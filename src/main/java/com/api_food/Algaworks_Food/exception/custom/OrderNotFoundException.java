package com.api_food.Algaworks_Food.exception.custom;

public class OrderNotFoundException extends EntityNotFoundException {
  private static final long serialVersionUID = 1L;

  public OrderNotFoundException(String message) {
    super(message);
  }

  public OrderNotFoundException(int orderId) {
    this(String.format("The order with id %d does not exist", orderId));
  }
}
