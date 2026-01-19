package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.StateCreateDTO;
import com.api_food.Algaworks_Food.dto.list.StateListDTO;
import com.api_food.Algaworks_Food.dto.update.StateUpdateDTO;
import com.api_food.Algaworks_Food.service.StateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/new")
    public ResponseEntity<StateCreateDTO> newState(@Valid @RequestBody StateCreateDTO state) {
        StateCreateDTO newState = stateService.createNewState(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(newState);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateListDTO> findStateById(@PathVariable int id) {
        StateListDTO stateFinded = stateService.findStateById(id);
        return ResponseEntity.status(HttpStatus.OK).body(stateFinded);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StateListDTO>> listStates() {
        List<StateListDTO> states = stateService.listStates();
        return ResponseEntity.status(HttpStatus.OK).body(states);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateUpdateDTO> updateState(@PathVariable int id, @Valid @RequestBody StateUpdateDTO state) {
        StateUpdateDTO stateUpdate = stateService.updateState(id, state);
        return ResponseEntity.status(HttpStatus.OK).body(stateUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteState(@PathVariable int id) {
        stateService.delState(id);
    }
}
