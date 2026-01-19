package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.domain.exception.custom.PermissionNotFoundException;
import com.api_food.Algaworks_Food.domain.model.PermissionModel;
import com.api_food.Algaworks_Food.domain.repository.PermissionRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    PermissionRepository permissionRepository;
    Formatter stringFormatter;

    public PermissionService(PermissionRepository permissionRepository, Formatter stringFormatter) {
        this.permissionRepository = permissionRepository;
        this.stringFormatter = stringFormatter;
    }

    public PermissionModel returnPermissionModel(int id){
        return permissionRepository.findById(id).orElseThrow(()-> new PermissionNotFoundException(id));
    }
}
