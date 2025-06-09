package com.api_food.Algaworks_Food.dto.update;

import com.api_food.Algaworks_Food.dto.list.StateListDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityUpdateDTO {
    private int id;
    @NotBlank(message = "City name is required")
    private String name;
    @Valid
    @NotNull(message = "State is required")
    private StateListDTO state;
}
