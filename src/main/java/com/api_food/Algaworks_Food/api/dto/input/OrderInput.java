package com.api_food.Algaworks_Food.api.dto.input;

import com.api_food.Algaworks_Food.api.dto.output.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderInput {
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
    private RestaurantOutput restaurant;
    @Valid
    @JsonIgnoreProperties(value = {"dateCreated", "dateUpdated"})
    private UserOutput user;
    @Valid
    private PaymentMethodOutput paymentMethod;
    @Valid
    private AddressOutput deliveryAddress;
    @Valid
    private List<ProductInOrderOutput> itens;
}
