package com.api_food.Algaworks_Food.exception.custom;

public class CityNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(int city_id){
        super(String.format("Could not find the city with the id: '%d'.", city_id));
    }
}
