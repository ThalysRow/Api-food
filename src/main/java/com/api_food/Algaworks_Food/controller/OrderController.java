package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.OrderCreateDTO;
import com.api_food.Algaworks_Food.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/new")
    public ResponseEntity<OrderCreateDTO> createNewOrder(@Valid @RequestBody OrderCreateDTO data) {
        OrderCreateDTO newOrder = orderService.createNewOrder(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }
}
