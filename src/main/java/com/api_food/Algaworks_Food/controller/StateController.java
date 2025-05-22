package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.StateDTO;
import com.api_food.Algaworks_Food.service.StateService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> findStateById(@PathVariable Id id){
        try {
            StateDTO state = stateService.findStateById(id);
            return ResponseEntity.status(HttpStatus.OK).body(state);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
