package com.api_food.Algaworks_Food.dto.list;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateListDTO {
    @NotNull
    @Positive
    private int id;
    private String name;
}
