package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.ProductCreateDTO;
import com.api_food.Algaworks_Food.dto.list.ProductListDTO;
import com.api_food.Algaworks_Food.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/new")
    public ResponseEntity<ProductCreateDTO> createProduct(@PathVariable UUID restaurantId, @RequestBody ProductCreateDTO data) {
        ProductCreateDTO productCreate = productService.createProduct(restaurantId, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductListDTO>> listProductsRestaurant(@PathVariable UUID restaurantId) {
        List<ProductListDTO> products = productService.listProductsRestaurant(restaurantId);

        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductListDTO> findProductInRestaurant(@PathVariable UUID restaurantId, @PathVariable int productId) {
        ProductListDTO product = productService.findProductInRestaurant(restaurantId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
