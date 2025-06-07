package com.api_food.Algaworks_Food.exception.custom;

public class StateNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public StateNotFoundException(int state_id){
        super(String.format("Could not found the state with the id: '%d'.", state_id));
    }
}
