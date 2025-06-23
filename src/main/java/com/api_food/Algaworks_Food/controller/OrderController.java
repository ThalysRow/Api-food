package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.OrderCreateDTO;
import com.api_food.Algaworks_Food.dto.list.OrderListDTO;
import com.api_food.Algaworks_Food.dto.list.OrderResumeListDTO;
import com.api_food.Algaworks_Food.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderListDTO> findOrderById(@PathVariable Integer id){
        OrderListDTO order = orderService.findOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResumeListDTO>> listAllOrders(){
        List<OrderResumeListDTO> orders = orderService.listAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
