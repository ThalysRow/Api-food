package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.CityInput;
import com.api_food.Algaworks_Food.api.dto.output.CityOutput;
import com.api_food.Algaworks_Food.domain.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public CityOutput newCity(@Valid @RequestBody CityInput city){
        return cityService.addCity(city);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CityOutput> listCities(){
        return cityService.listCities();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityOutput findCity(@PathVariable int id){
        return cityService.findCity(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable int id){
        cityService.deleteCity(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityOutput updateCity(@PathVariable int id, @Valid @RequestBody CityInput city){
        return cityService.updateCity(id, city);
    }
}
