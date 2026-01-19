package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.service.OrderFlowService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{orderId}")
public class OrderFlowController {

    private final OrderFlowService orderFlowService;

    public OrderFlowController(OrderFlowService orderFlowService) {
        this.orderFlowService = orderFlowService;
    }

    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmOrder(@PathVariable int orderId){
        orderFlowService.confirmOrder(orderId);
    }

    @PutMapping("/deliver")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deliverOrder(@PathVariable int orderId){
        orderFlowService.deliverOrder(orderId);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable int orderId){
        orderFlowService.cancelOrder(orderId);
    }
}
