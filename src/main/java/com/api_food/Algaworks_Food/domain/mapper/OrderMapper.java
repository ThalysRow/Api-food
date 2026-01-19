package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.input.OrderInput;
import com.api_food.Algaworks_Food.api.dto.output.OrderOutput;
import com.api_food.Algaworks_Food.api.dto.output.OrderResumeOutput;
import com.api_food.Algaworks_Food.api.dto.output.ProductInOrderOutput;
import com.api_food.Algaworks_Food.domain.model.OrderItemModel;
import com.api_food.Algaworks_Food.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "itens", source = "itens")
    OrderModel toModel(OrderInput input);

    @Mapping(target = "dateCancelled", source = "dateCancelled")
    OrderOutput toList(OrderModel orderModel);

    OrderResumeOutput toResumeOutput(OrderModel orderModel);

    @Mapping(target = "itens", source = "itens")
    OrderOutput toOutput(OrderModel orderModel);

    @Named("mapOrderItems")
    List<ProductInOrderOutput> mapOrderItems(List<OrderItemModel> itens);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "unitPrice", source = "unitPrice")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "observations", source = "observations")
    ProductInOrderOutput mapOrderItem(OrderItemModel orderItem);
}
