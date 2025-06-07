package com.api_food.Algaworks_Food.exception.handler;

import lombok.Getter;

@Getter
public enum ProblemType {
    UNREADABLE_MESSAGE("/unreadable-message", "Unreadable message"),
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter");

    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
