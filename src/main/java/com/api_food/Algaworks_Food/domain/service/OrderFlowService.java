package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.domain.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFlowService {

    private final OrderService orderService;
    private final EmailSendService emailSendService;

    @Transactional
    public void confirmOrder(int orderId){
        OrderModel order = orderService.returnOrderModel(orderId);

        order.confirm();

//        var email = EmailSendService.EmailMessage.builder()
//                        .subject(order.getRestaurant().getName() + " Order number: " + orderId)
//                        .body("Order-confirmed.html")
//                        .model("orders", order)
//                        .recipient(order.getUser().getEmail()).build();
//
//        emailSendService.send(email);

    }

    @Transactional
    public void deliverOrder(int orderId){
        OrderModel order = orderService.returnOrderModel(orderId);
         order.deliver();
    }

    @Transactional
    public void cancelOrder(int orderId){
        OrderModel order = orderService.returnOrderModel(orderId);

        order.cancel();
    }
}
