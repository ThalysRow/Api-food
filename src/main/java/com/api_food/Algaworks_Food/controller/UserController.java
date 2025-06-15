package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.UserCreateDTO;
import com.api_food.Algaworks_Food.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
