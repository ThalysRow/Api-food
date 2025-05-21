package com.api_food.Algaworks_Food.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
    private int id;
    @NotBlank(message = "state name is required")
    private String name;
}
