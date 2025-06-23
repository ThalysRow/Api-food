package com.api_food.Algaworks_Food.dto.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResumeListDTO {
    private int id;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalValue;
    private String status;
    private OffsetDateTime dateCreated;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime dateCancelled;
    @JsonIgnoreProperties(value = {"deliveryFee", "kitchen", "dateCreated", "dateUpdated",
            "active", "open", "address", "user"})
    private RestaurantListDTO restaurant;
    @JsonIgnoreProperties(value = {"dateCreated", "dateUpdated"})
    private UserListDTO user;
}
