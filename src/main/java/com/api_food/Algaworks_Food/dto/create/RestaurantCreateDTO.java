package com.api_food.Algaworks_Food.dto.create;

import com.api_food.Algaworks_Food.dto.list.KitchenListDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCreateDTO {

    private UUID id;

    @NotBlank(message = "Restaurant name is required")
    private String name;

    @PositiveOrZero(message = "The delivery fee must be positive")
    @NotNull(message = "The delivery fee is required")
    private BigDecimal deliveryFee;

    @Valid
    @NotNull(message = "Kitchen is required")
    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private KitchenListDTO kitchen;

}
