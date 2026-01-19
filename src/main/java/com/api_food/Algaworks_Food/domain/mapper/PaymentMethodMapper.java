package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.PaymentMethodOutput;
import com.api_food.Algaworks_Food.domain.model.PaymentMethodModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodOutput toOutput(PaymentMethodModel paymentMethodModel);
}
