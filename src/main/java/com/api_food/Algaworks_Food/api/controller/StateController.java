package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.StateInput;
import com.api_food.Algaworks_Food.api.dto.output.StateOutput;
import com.api_food.Algaworks_Food.domain.service.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public StateOutput newState(@Valid @RequestBody StateInput input) {
        return stateService.createNewState(input);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StateOutput findStateById(@PathVariable int id) {
        return stateService.findStateById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<StateOutput> listStates() {
      return stateService.listStates();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StateOutput updateState(@PathVariable int id, @Valid @RequestBody StateInput input) {
       return stateService.updateState(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteState(@PathVariable int id) {
        stateService.delState(id);
    }
}
