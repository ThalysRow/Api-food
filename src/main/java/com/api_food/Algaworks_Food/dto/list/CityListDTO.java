package com.api_food.Algaworks_Food.dto.list;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityListDTO {
    @NotNull(message = "City id is required")
    @Positive(message = "City id must be a valid positive number.")
    private int id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    private StateListDTO state;
}
