package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.StateDTO;
import com.api_food.Algaworks_Food.service.StateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/states")
public class StateController {
    private StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/new")
    public ResponseEntity<StateDTO> newState(@Valid @RequestBody StateDTO state){
        StateDTO newState = stateService.createNewState(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(newState);
    }
}
