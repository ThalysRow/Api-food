package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.PaymentMethodCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.PaymentMethodListDTO;
import com.api_food.Algaworks_Food.api.dto.update.PaymentMethodUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.PaymentMethodModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodCreateDTO toCreateDTO(PaymentMethodModel paymentMethodModel);
    PaymentMethodModel toCreateModel(PaymentMethodCreateDTO paymentMethodCreateDTO);
    PaymentMethodListDTO toListDTO(PaymentMethodModel paymentMethodModel);
    PaymentMethodModel toSaveModel(PaymentMethodListDTO paymentMethodListDTO);
    PaymentMethodUpdateDTO toUpdateDTO(PaymentMethodModel paymentMethodModel);
}
