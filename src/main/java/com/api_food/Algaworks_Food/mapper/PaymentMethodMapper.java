package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.PaymentMethodCreateDTO;
import com.api_food.Algaworks_Food.dto.list.PaymentMethodListDTO;
import com.api_food.Algaworks_Food.model.PaymentMethodModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodCreateDTO toCreateDTO(PaymentMethodModel paymentMethodModel);
    PaymentMethodModel toCreateModel(PaymentMethodCreateDTO paymentMethodCreateDTO);
    PaymentMethodListDTO toListDTO(PaymentMethodModel paymentMethodModel);
}
