package com.api_food.Algaworks_Food.domain.exception.custom;

public class PhotoStorageException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public PhotoStorageException(Throwable cause) {
        super("Failed to upload product image to storage");
        initCause(cause);
    }

}
