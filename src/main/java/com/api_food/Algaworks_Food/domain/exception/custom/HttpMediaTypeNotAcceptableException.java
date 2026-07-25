package com.api_food.Algaworks_Food.domain.exception.custom;

public class HttpMediaTypeNotAcceptableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpMediaTypeNotAcceptableException(String format) {
        super(String.format("The image format %s is not supported by the client", format));
    }
}
