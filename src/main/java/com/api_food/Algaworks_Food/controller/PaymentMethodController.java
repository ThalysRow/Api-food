package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.PaymentMethodCreateDTO;
import com.api_food.Algaworks_Food.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentMethod")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping("/new")
    public ResponseEntity<PaymentMethodCreateDTO> createPaymentMethod(@Valid @RequestBody PaymentMethodCreateDTO data){
            PaymentMethodCreateDTO newPayment = paymentMethodService.addPaymentMethod(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPayment);
    }
}
