package com.api_food.Algaworks_Food.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantListDTO {

    private UUID id;
    private String name;
    private BigDecimal deliveryFee;
    private KitchenListDTO kitchen;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
