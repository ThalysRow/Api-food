package com.api_food.Algaworks_Food.dto;

import com.api_food.Algaworks_Food.model.StateModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    @NotBlank(message = "City name is required")
    private String name;
    @NotNull(message = "State_id is required")
    private StateModel state_id;
}
