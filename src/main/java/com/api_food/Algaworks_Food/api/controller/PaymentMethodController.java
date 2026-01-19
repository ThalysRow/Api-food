package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.PaymentMethodInput;
import com.api_food.Algaworks_Food.api.dto.output.PaymentMethodOutput;
import com.api_food.Algaworks_Food.domain.service.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paymentMethod")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodOutput createPaymentMethod(@Valid @RequestBody PaymentMethodInput input){
    return paymentMethodService.addPaymentMethod(input);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentMethodOutput> listAllPaymentMethods(){
        return paymentMethodService.listAllPaymentMethod();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentMethodOutput findPaymentMethod(@PathVariable int id){
        return paymentMethodService.findPaymentMethodById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentMethodOutput updatePaymentMethod(@PathVariable int id,
                                                   @Valid @RequestBody PaymentMethodInput input){
        return paymentMethodService.updatePaymentMethod(id, input);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaymentMethod(@PathVariable int id){
        paymentMethodService.deletePaymentMethod(id);
    }
}
