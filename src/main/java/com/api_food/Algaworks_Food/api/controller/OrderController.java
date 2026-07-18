package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.OrderFilter;
import com.api_food.Algaworks_Food.api.dto.input.OrderInput;
import com.api_food.Algaworks_Food.api.dto.output.OrderOutput;
import com.api_food.Algaworks_Food.api.dto.output.OrderResumeOutput;
import com.api_food.Algaworks_Food.domain.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderOutput addNewOrder(@Valid @RequestBody OrderInput input) {
        return orderService.createNewOrder(input);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderOutput findOrderById(@PathVariable Integer id){
        return orderService.findOrderById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResumeOutput> searchOrders(@ModelAttribute OrderFilter filter) {
        return orderService.listOrders(filter);
    }
}
