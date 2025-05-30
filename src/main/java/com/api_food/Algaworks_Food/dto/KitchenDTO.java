package com.api_food.Algaworks_Food.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenDTO {
    private UUID id;
    @NotBlank(message = "Kitchen name is required")
    private String name;
    private List<RestaurantDTO> restaurants;
}
