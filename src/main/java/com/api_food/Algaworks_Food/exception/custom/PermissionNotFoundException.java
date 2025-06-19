package com.api_food.Algaworks_Food.exception.custom;

public class PermissionNotFoundException extends EntityNotFoundException {
    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(int permissionId, int groupId) {
      this(String.format("There is no permission with id %d for group id %d", permissionId, groupId));
    }

    public PermissionNotFoundException(int permissionId) {
      this(String.format("There is no permission with id %d", permissionId));
    }
}
