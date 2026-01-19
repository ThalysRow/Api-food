package com.api_food.Algaworks_Food.api.dto.output;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenOutput {
    @NotNull(message = "The kitchen id is required")
    private UUID id;
    private String name;
}
