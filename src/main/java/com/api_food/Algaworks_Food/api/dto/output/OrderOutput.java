package com.api_food.Algaworks_Food.api.dto.output;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderOutput {
    private int id;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalValue;
    private String status;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateConfirmed;
    private OffsetDateTime dateDelivered;
    private OffsetDateTime dateCancelled;
    @JsonIgnoreProperties(value = {"deliveryFee", "kitchen", "dateCreated", "dateUpdated",
            "active", "open", "address", "user"})
    private RestaurantOutput restaurant;
    @JsonIgnoreProperties(value = {"dateCreated", "dateUpdated"})
    private UserOutput user;
    private PaymentMethodOutput paymentMethod;
    private AddressOutput deliveryAddress;
    private List<ProductInOrderOutput> itens;
}
