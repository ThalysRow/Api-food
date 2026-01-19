package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.ProductInput;
import com.api_food.Algaworks_Food.api.dto.output.ProductOutput;
import com.api_food.Algaworks_Food.domain.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOutput createProduct(@PathVariable UUID restaurantId,
                                       @Valid @RequestBody ProductInput input) {
       return productService.createProduct(restaurantId, input);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductOutput> listProductsRestaurant(@PathVariable UUID restaurantId) {
        return productService.listProductsInRestaurant(restaurantId);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductOutput findProductInRestaurant(@PathVariable UUID restaurantId,
                                                                  @PathVariable int productId) {
       return productService.findProductInRestaurant(restaurantId, productId);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductOutput updateProductRestaurant(@PathVariable UUID restaurantId,
                                                 @PathVariable int productId,
                                                 @Valid @RequestBody ProductInput input) {
       return productService.updateProductInRestaurant(restaurantId, productId, input);
    }
}
