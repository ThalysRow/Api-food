package com.api_food.Algaworks_Food.dto.list;

import com.api_food.Algaworks_Food.dto.create.AddressCreateDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDTO {
    private int id;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalValue;
    private String status;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateConfirmed;
    private OffsetDateTime dateDelivered;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime dateCancelled;
    @JsonIgnoreProperties(value = {"deliveryFee", "kitchen", "dateCreated", "dateUpdated",
            "active", "open", "address", "user"})
    private RestaurantListDTO restaurant;
    @JsonIgnoreProperties(value = {"dateCreated", "dateUpdated"})
    private UserListDTO user;
    private PaymentMethodListDTO paymentMethod;
    private AddressCreateDTO deliveryAddress;
    private List<ListProductInOrderDTO> itens;
}
