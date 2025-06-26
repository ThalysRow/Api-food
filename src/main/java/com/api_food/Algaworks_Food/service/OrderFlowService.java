package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.enums.OrderStatus;
import com.api_food.Algaworks_Food.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.model.OrderModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class OrderFlowService {

    private final OrderService orderService;

    public OrderFlowService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void confirmOrder(int orderId){
        OrderModel order = orderService.returnOrderModel(orderId);

        if (!order.getStatus().equals(OrderStatus.CREATED)){
            throw new BusinessException(String.format("The order with id '%d' cannot be confirmed, " +
                    "because the status of the order is '%s'",orderId,order.getStatus()));
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order.setDateConfirmed(OffsetDateTime.now());
    }

    @Transactional
    public void deliverOrder(int orderId){
        OrderModel order = orderService.returnOrderModel(orderId);

        if(!order.getStatus().equals(OrderStatus.CONFIRMED)){
            throw new BusinessException(String.format("The order with id '%d' cannot be delivered, " +
                    "because the status of the order is '%s'",orderId,order.getStatus()));
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setDateDelivered(OffsetDateTime.now());
    }

    @Transactional
    public void cancelOrder(int orderId){
        OrderModel order = orderService.returnOrderModel(orderId);

        if(!order.getStatus().equals(OrderStatus.CONFIRMED) &&
                !order.getStatus().equals(OrderStatus.CREATED)){
            throw new BusinessException(String.format("The order with id '%d' cannot be cancelled, " +
                    "because the status of the order is '%s'",orderId,order.getStatus()));
        }

        order.setStatus(OrderStatus.CANCELED);
        order.setDateCancelled(OffsetDateTime.now());

    }
}
