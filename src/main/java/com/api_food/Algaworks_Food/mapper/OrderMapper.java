package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.OrderCreateDTO;
import com.api_food.Algaworks_Food.dto.list.ListProductInOrderDTO;
import com.api_food.Algaworks_Food.model.OrderItemModel;
import com.api_food.Algaworks_Food.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "itens", source = "itens")
    OrderModel toCreateModel(OrderCreateDTO orderCreateDTO);

    @Mapping(target = "itens", source = "itens")
    OrderCreateDTO toCreateDTO(OrderModel orderModel);

    List<ListProductInOrderDTO> mapOrderItems(List<OrderItemModel> itens);

    @Mapping(target = "id", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "unitPrice", source = "unitPrice")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "observations", source = "observations")
    ListProductInOrderDTO mapOrderItem(OrderItemModel orderItem);
}
