package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.CityDTO;
import com.api_food.Algaworks_Food.service.CityService;
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
    public ResponseEntity<Object> newCity(@RequestBody CityDTO city){
        try {
            CityDTO newCity = cityService.addCity(city);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CityDTO>> listCities(){
        List<CityDTO> cities = cityService.listCities();
        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findCity(@PathVariable int id){
        try {
            CityDTO city = cityService.findCity(id);
            return ResponseEntity.status(HttpStatus.OK).body(city);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCity(@PathVariable int id){
        try {
            cityService.deleteCity(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
