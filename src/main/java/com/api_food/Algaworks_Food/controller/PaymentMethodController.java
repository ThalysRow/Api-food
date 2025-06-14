package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.PaymentMethodCreateDTO;
import com.api_food.Algaworks_Food.dto.list.PaymentMethodListDTO;
import com.api_food.Algaworks_Food.dto.update.PaymentMethodUpdateDTO;
import com.api_food.Algaworks_Food.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<PaymentMethodListDTO>> listAllPaymentMethods(){
        List<PaymentMethodListDTO> paymentMethods = paymentMethodService.listAllPaymentMethod();
        return ResponseEntity.status(HttpStatus.OK).body(paymentMethods);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodListDTO> findPaymentMethod(@PathVariable int id){
        PaymentMethodListDTO paymentMethod = paymentMethodService.findPaymentMethodById(id);

        return ResponseEntity.status(HttpStatus.OK).body(paymentMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodUpdateDTO> updatePaymentMethod(@PathVariable int id, @Valid @RequestBody PaymentMethodUpdateDTO data){
        PaymentMethodUpdateDTO paymentMethod = paymentMethodService.updatePaymentMethod(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(paymentMethod);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaymentMethod(@PathVariable int id){
        paymentMethodService.deletePaymentMethod(id);
    }
}
