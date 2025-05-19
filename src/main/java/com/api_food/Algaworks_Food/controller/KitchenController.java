package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.KitchenDTO;
import com.api_food.Algaworks_Food.service.KitchenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> findKitchen(@PathVariable UUID id){
        try {
            KitchenDTO kitchenFinded = kitchenService.findKitchenById(id);
            return ResponseEntity.ok(kitchenFinded);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
