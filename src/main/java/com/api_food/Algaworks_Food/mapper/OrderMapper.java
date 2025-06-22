package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.OrderCreateDTO;
import com.api_food.Algaworks_Food.dto.create.OrderItemCreateDTO;
import com.api_food.Algaworks_Food.model.OrderItemModel;
import com.api_food.Algaworks_Food.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "itens", source = "itens")
    OrderModel toCreateModel(OrderCreateDTO orderCreateDTO);

    @Mapping(target = "product.description", ignore = true)
    @Mapping(target = "product.active", ignore = true)
    OrderCreateDTO toCreateDTO(OrderModel orderModel);
    List<OrderItemCreateDTO> mapOrderItems(List<OrderItemModel> itens);
}
