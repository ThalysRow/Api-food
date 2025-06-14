package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.GroupCreateDTO;
import com.api_food.Algaworks_Food.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/new")
    public ResponseEntity<GroupCreateDTO> newGroup(@Valid @RequestBody GroupCreateDTO data){
        GroupCreateDTO addGroup = groupService.addGroup(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(addGroup);
    }
}
