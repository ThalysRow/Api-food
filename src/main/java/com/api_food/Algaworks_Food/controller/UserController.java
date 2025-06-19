package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.UserCreateDTO;
import com.api_food.Algaworks_Food.dto.list.GroupListDTO;
import com.api_food.Algaworks_Food.dto.list.UserListDTO;
import com.api_food.Algaworks_Food.dto.update.UserUpdateDTO;
import com.api_food.Algaworks_Food.dto.update.UserUpdatePasswordDTO;
import com.api_food.Algaworks_Food.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<UserCreateDTO> createNewUser(@Valid @RequestBody UserCreateDTO data){
        UserCreateDTO newUser = userService.addNewUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserListDTO> findUser(@PathVariable UUID id){
        UserListDTO user = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserListDTO>> listUsers(){
        List<UserListDTO> users = userService.listAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO data){
        UserUpdateDTO updateUser = userService.updateUser(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserPassword(@PathVariable UUID id, @Valid @RequestBody UserUpdatePasswordDTO data){
        userService.updateUserPassword(id, data);
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
    public ResponseEntity<List<GroupListDTO>> listGroupUserHave(@PathVariable UUID userId){
        List<GroupListDTO> groups = userService.listGroupsUserHave(userId);
        return ResponseEntity.status(HttpStatus.OK).body(groups);
    }
}
