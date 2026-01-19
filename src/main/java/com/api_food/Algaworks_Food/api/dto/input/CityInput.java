package com.api_food.Algaworks_Food.api.dto.input;

import com.api_food.Algaworks_Food.api.dto.output.StateOutput;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInput {
    @NotBlank(message = "City name is required")
    private String name;
    @Valid
    @NotNull(message = "State is required")
    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private StateOutput state;
}
