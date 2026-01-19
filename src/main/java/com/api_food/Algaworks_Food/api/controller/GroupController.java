package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.GroupInput;
import com.api_food.Algaworks_Food.api.dto.output.GroupOutput;
import com.api_food.Algaworks_Food.api.dto.output.PermissionsOutput;
import com.api_food.Algaworks_Food.domain.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupOutput addGroup(@Valid @RequestBody GroupInput input){
        return groupService.addGroup(input);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GroupOutput findGroup(@PathVariable int id){
        return groupService.findGroupById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<GroupOutput> listAllGroups(){
        return groupService.listAllGroups();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GroupOutput updateGroup(@PathVariable int id, @Valid @RequestBody GroupInput input){
       return groupService.updateGroup(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable int id){
        groupService.deleteGroup(id);
    }

    @GetMapping("{groupId}/permissions")
    @ResponseStatus(HttpStatus.OK)
    public List<PermissionsOutput> listGroupPermissions(@PathVariable int groupId){
        return groupService.listGroupPermissions(groupId);
    }

    @DeleteMapping("/{groupId}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGroupPermissions(@PathVariable int groupId, @PathVariable int permissionId){
        groupService.removeGroupPermissions(groupId, permissionId);
    }

    @PutMapping("/{groupId}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addGroupPermissions(@PathVariable int groupId, @PathVariable int permissionId){
        groupService.addGroupPermissions(groupId, permissionId);
    }
}
