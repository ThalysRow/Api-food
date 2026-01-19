package com.api_food.Algaworks_Food.api.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateInput {
    private int id;
    @NotBlank(message = "state name is required")
    private String name;
}
