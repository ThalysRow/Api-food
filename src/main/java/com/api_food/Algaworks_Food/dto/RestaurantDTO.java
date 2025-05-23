package com.api_food.Algaworks_Food.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private UUID id;
    @NotBlank(message = "Restaurant name is required")
    private String name;
    @Positive(message = "The delivery fee must be positive")
    @NotNull(message = "The delivery fee is required")
    private BigDecimal deliveryFee;
    @NotNull(message = "The kitchen is required")
    private KitchenDTO kitchen;
}
