package com.api_food.Algaworks_Food.exception.custom;

import java.util.UUID;

public class EntityInUseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityInUseException(String message) {
        super(message);
    }

    public EntityInUseException(String entityName, UUID entityId, String tableName){
        this(String.format("'%s' id '%s' cannot be deleted, it is in use by '%s' table.", entityName, entityId, tableName));
    }

    public EntityInUseException(String entityName, int entityId, String tableName){
        this(String.format("'%s' id '%s' cannot be deleted, it is in use by '%s' table.", entityName, entityId, tableName));
    }
}
