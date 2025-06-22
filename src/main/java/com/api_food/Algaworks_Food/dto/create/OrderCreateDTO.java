package com.api_food.Algaworks_Food.dto.create;

import com.api_food.Algaworks_Food.dto.list.PaymentMethodListDTO;
import com.api_food.Algaworks_Food.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.dto.list.UserListDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal subtotal;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal deliveryFee;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalValue;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dateCreated;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dateConfirmed;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dateDelivered;
    @Valid
    @JsonIgnoreProperties(value = {"deliveryFee", "kitchen", "dateCreated", "dateUpdated",
            "active", "open", "address", "user"})
    private RestaurantListDTO restaurant;
    @Valid
    @JsonIgnoreProperties(value = {"dateCreated", "dateUpdated"})
    private UserListDTO user;
    @Valid
    private PaymentMethodListDTO paymentMethod;
    @Valid
    private AddressCreateDTO deliveryAddress;
    @Valid
    private List<OrderItemCreateDTO> itens;
}
