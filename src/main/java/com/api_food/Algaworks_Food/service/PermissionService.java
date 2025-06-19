package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.exception.custom.PermissionNotFoundException;
import com.api_food.Algaworks_Food.model.PermissionModel;
import com.api_food.Algaworks_Food.repository.PermissionRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    PermissionRepository permissionRepository;
    StringFormatter stringFormatter;

    public PermissionService(PermissionRepository permissionRepository, StringFormatter stringFormatter) {
        this.permissionRepository = permissionRepository;
        this.stringFormatter = stringFormatter;
    }

    public PermissionModel returnPermissionModel(int id){
        return permissionRepository.findById(id).orElseThrow(()-> new PermissionNotFoundException(id));
    }
}
