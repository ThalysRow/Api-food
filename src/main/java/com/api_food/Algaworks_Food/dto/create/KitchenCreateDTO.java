package com.api_food.Algaworks_Food.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenCreateDTO {
    private UUID id;
    @NotBlank(message = "Kitchen name is required")
    private String name;
}
