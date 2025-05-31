package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.CityCreateDTO;
import com.api_food.Algaworks_Food.dto.list.CityListDTO;
import com.api_food.Algaworks_Food.dto.update.CityUpdateDTO;
import com.api_food.Algaworks_Food.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/new")
    public ResponseEntity<CityCreateDTO> newCity(@Valid @RequestBody CityCreateDTO city){
        CityCreateDTO newCity = cityService.addCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CityListDTO>> listCities(){
        List<CityListDTO> cities = cityService.listCities();
        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityListDTO> findCity(@PathVariable int id){
        CityListDTO city = cityService.findCity(id);
        return ResponseEntity.status(HttpStatus.OK).body(city);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable int id){
        cityService.deleteCity(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityUpdateDTO> updateCity(@PathVariable int id, @Valid @RequestBody CityUpdateDTO city){
        CityUpdateDTO updateCity = cityService.updateCity(id, city);
        return ResponseEntity.status(HttpStatus.OK).body(updateCity);
    }
}
