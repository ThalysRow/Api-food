package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.KitchenDTO;
import com.api_food.Algaworks_Food.service.KitchenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchen")
public class KitchenController {
    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @PostMapping("/create")
    public ResponseEntity<KitchenDTO> registerKitchen(@Valid @RequestBody KitchenDTO kitchen){
        KitchenDTO saveKitchen = kitchenService.addKitchen(kitchen);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveKitchen);
    }
}
