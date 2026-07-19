package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.KitchenInput;
import com.api_food.Algaworks_Food.api.dto.output.KitchenOutput;
import com.api_food.Algaworks_Food.api.dto.output.PageResponseOutput;
import com.api_food.Algaworks_Food.domain.service.KitchenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/kitchens")
@RequiredArgsConstructor
public class KitchenController {
    private final KitchenService kitchenService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOutput registerKitchen(@Valid @RequestBody KitchenInput input){
        return kitchenService.addKitchen(input);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KitchenOutput findKitchen(@PathVariable UUID id){
        return kitchenService.findKitchenById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public PageResponseOutput<KitchenOutput> listKitchens(@PageableDefault(size = 10) Pageable pageable){
        return kitchenService.listKitchens(pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKitchen(@PathVariable UUID id){
        kitchenService.deleteKitchen(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KitchenOutput updateKitchen(@PathVariable UUID id, @Valid @RequestBody KitchenInput input){
        return kitchenService.updateKitchen(id, input);
    }
}
