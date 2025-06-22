package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.PaymentMethodCreateDTO;
import com.api_food.Algaworks_Food.dto.list.PaymentMethodListDTO;
import com.api_food.Algaworks_Food.dto.update.PaymentMethodUpdateDTO;
import com.api_food.Algaworks_Food.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.exception.custom.PaymentMethodAlreadyExistsException;
import com.api_food.Algaworks_Food.exception.custom.PaymentMethodNotFoundException;
import com.api_food.Algaworks_Food.mapper.PaymentMethodMapper;
import com.api_food.Algaworks_Food.model.PaymentMethodModel;
import com.api_food.Algaworks_Food.repository.PaymentMethodRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;
    private final StringFormatter stringFormatter;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository, PaymentMethodMapper paymentMethodMapper, StringFormatter stringFormatter) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
        this.stringFormatter = stringFormatter;
    }

    public void verifyPaymentMethodName(String name){
        Optional<PaymentMethodModel> findName = paymentMethodRepository.findByName(name);

        if (findName.isPresent()) {
            throw new PaymentMethodAlreadyExistsException(name);
        }
    }

    public void verifyPaymentMethodInUse(int id){
        PaymentMethodModel findPaymentMethod = this.returnPaymentMethodModel(id);

        if (findPaymentMethod.getOrders() != null && !findPaymentMethod.getOrders().isEmpty() ||
                findPaymentMethod.getRestaurants() != null && !findPaymentMethod.getRestaurants().isEmpty()){
            throw new EntityInUseException(findPaymentMethod.getName(), id, "orders and restaurants");
        }
    }

    @Transactional
    public PaymentMethodCreateDTO addPaymentMethod(PaymentMethodCreateDTO paymentMethod){

        String nameFormated = stringFormatter.stringFormated(paymentMethod.getName()).toUpperCase();
        paymentMethod.setName(nameFormated);

        this.verifyPaymentMethodName(nameFormated);

        PaymentMethodModel newPayment = paymentMethodMapper.toCreateModel(paymentMethod);
        PaymentMethodModel savePaymentMethod = paymentMethodRepository.save(newPayment);

        return paymentMethodMapper.toCreateDTO(savePaymentMethod);
    }

    public List<PaymentMethodListDTO> listAllPaymentMethod(){
        return paymentMethodRepository.findAll().stream().map(paymentMethodMapper::toListDTO).toList();
    }

    public PaymentMethodListDTO findPaymentMethodById(int id){
        PaymentMethodModel payment = this.returnPaymentMethodModel(id);

        return paymentMethodMapper.toListDTO(payment);
    }

    @Transactional
    public PaymentMethodUpdateDTO updatePaymentMethod(int id, PaymentMethodUpdateDTO data){
        PaymentMethodModel payment = this.returnPaymentMethodModel(id);
        String nameFormated = stringFormatter.stringFormated(data.getName()).toUpperCase();
        this.verifyPaymentMethodName(nameFormated);
        payment.setName(nameFormated);
        PaymentMethodModel saveUpdatePaymentMethod = paymentMethodRepository.save(payment);
        return paymentMethodMapper.toUpdateDTO(saveUpdatePaymentMethod);
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

    public PaymentMethodModel verifyPaymentField(int id){
        return   paymentMethodRepository.findById(id)
                .orElseThrow(() -> new BusinessException("payment", id));
    }
}
