package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.GroupCreateDTO;
import com.api_food.Algaworks_Food.dto.list.GroupListDTO;
import com.api_food.Algaworks_Food.dto.update.GroupUpdateDTO;
import com.api_food.Algaworks_Food.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<GroupListDTO> findGroup(@PathVariable int id){
        GroupListDTO group = groupService.findGroupById(id);
        return ResponseEntity.status(HttpStatus.OK).body(group);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupListDTO>> listAllGroups(){
        List<GroupListDTO> groups = groupService.listAllGroups();
        return ResponseEntity.status(HttpStatus.OK).body(groups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupUpdateDTO> updateGroup(@PathVariable int id, @Valid @RequestBody GroupUpdateDTO data){
        GroupUpdateDTO updateGroup = groupService.updateGroup(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updateGroup);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable int id){
        groupService.deleteGroup(id);
    }
}
