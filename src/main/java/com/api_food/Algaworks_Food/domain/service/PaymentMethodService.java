package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.PaymentMethodInput;
import com.api_food.Algaworks_Food.api.dto.output.PaymentMethodOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.exception.custom.PaymentMethodNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.PaymentMethodMapper;
import com.api_food.Algaworks_Food.domain.model.PaymentMethodModel;
import com.api_food.Algaworks_Food.domain.repository.PaymentMethodRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    private void verifyPaymentMethodInUse(int id){
        PaymentMethodModel findPaymentMethod = this.returnPaymentMethodModel(id);

        if (findPaymentMethod.getOrders() != null && !findPaymentMethod.getOrders().isEmpty() ||
                findPaymentMethod.getRestaurants() != null && !findPaymentMethod.getRestaurants().isEmpty()){
            throw new EntityInUseException(findPaymentMethod.getName(), id, "orders and restaurants");
        }
    }

    @Transactional
    public PaymentMethodOutput addPaymentMethod(PaymentMethodInput input){

        String name = Formatter.string(input.getName().toUpperCase());

        PaymentMethodModel payment = PaymentMethodModel.addPayment(name);
        paymentMethodRepository.saveAndFlush(payment);

        return paymentMethodMapper.toOutput(payment);
    }

    public List<PaymentMethodOutput> listAllPaymentMethod(){
        return paymentMethodRepository.findAll().stream().map(paymentMethodMapper::toOutput).toList();
    }

    public PaymentMethodOutput findPaymentMethodById(int id){
        PaymentMethodModel payment = this.returnPaymentMethodModel(id);

        return paymentMethodMapper.toOutput(payment);
    }

    @Transactional
    public PaymentMethodOutput updatePaymentMethod(int id, PaymentMethodInput input){

        String name = Formatter.string(input.getName().toUpperCase());

        PaymentMethodModel payment = this.returnPaymentMethodModel(id);

        payment.setName(name);

        paymentMethodRepository.saveAndFlush(payment);

        return paymentMethodMapper.toOutput(payment);
    }

    @Transactional
    public void deletePaymentMethod(int id){
        this.verifyPaymentMethodInUse(id);
        paymentMethodRepository.deleteById(id);
    }

    public PaymentMethodModel returnPaymentMethodModel(int id){
        return  paymentMethodRepository.findById(id)
                .orElseThrow(()-> new PaymentMethodNotFoundException(id));
    }
}
