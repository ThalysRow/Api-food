package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.KitchenCreateDTO;
import com.api_food.Algaworks_Food.dto.list.KitchenListDTO;
import com.api_food.Algaworks_Food.dto.update.KitchenUpdateDTO;
import com.api_food.Algaworks_Food.service.KitchenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {
    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @PostMapping("/create")
    public ResponseEntity<KitchenCreateDTO> registerKitchen(@Valid @RequestBody KitchenCreateDTO kitchen){
        KitchenCreateDTO saveKitchen = kitchenService.addKitchen(kitchen);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveKitchen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KitchenListDTO> findKitchen(@PathVariable UUID id){
        KitchenListDTO kitchen = kitchenService.findKitchenById(id);
        return ResponseEntity.status(HttpStatus.OK).body(kitchen);
    }

    @GetMapping("/all")
    public ResponseEntity<List<KitchenListDTO>> listKitchens(){
        List<KitchenListDTO> kitchens = kitchenService.listKitchens();
        return ResponseEntity.status(HttpStatus.OK).body(kitchens);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKitchen(@PathVariable UUID id){
        kitchenService.deleteKitchen(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KitchenUpdateDTO> updateKitchen(@PathVariable UUID id, @Valid @RequestBody KitchenUpdateDTO kitchen){
        KitchenUpdateDTO kitchenSaved = kitchenService.updateKitchen(id, kitchen);
        return ResponseEntity.status(HttpStatus.OK).body(kitchenSaved);
    }
}
