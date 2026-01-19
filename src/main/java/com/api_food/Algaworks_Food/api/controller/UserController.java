package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.UserInput;
import com.api_food.Algaworks_Food.api.dto.input.UserUpdateInput;
import com.api_food.Algaworks_Food.api.dto.input.UserUpdatePasswordInput;
import com.api_food.Algaworks_Food.api.dto.output.GroupOutput;
import com.api_food.Algaworks_Food.api.dto.output.UserOutput;
import com.api_food.Algaworks_Food.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput createNewUser(@Valid @RequestBody UserInput input){
        return userService.addNewUser(input);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput findUser(@PathVariable UUID id){
        return userService.findUserById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserOutput> listUsers(){
        return userService.listAllUsers();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateInput input){
        return userService.updateUser(id, input);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserPassword(@PathVariable UUID id, @Valid @RequestBody UserUpdatePasswordInput input){
        userService.updateUserPassword(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
    }

    @PutMapping("{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addGroupUser(@PathVariable UUID userId, @PathVariable int groupId){
        userService.addGroupToUser(userId, groupId);
    }

    @GetMapping("/{userId}/groups/list")
    @ResponseStatus(HttpStatus.OK)
    public List<GroupOutput> listGroupUserHave(@PathVariable UUID userId){
        return userService.listGroupsFromUser(userId);
    }

    @DeleteMapping("{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGroupFromUser(@PathVariable UUID userId, @PathVariable int groupId){
        userService.removeGroupFromUser(userId, groupId);
    }
}
