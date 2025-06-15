package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.UserCreateDTO;
import com.api_food.Algaworks_Food.dto.list.UserListDTO;
import com.api_food.Algaworks_Food.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
