package com.api_food.Algaworks_Food.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    private int id;
    @NotBlank(message = "The field name is required")
    private String name;
    @NotBlank(message = "The field description is required")
    private String description;
    @Positive(message = "The price value must be positive")
    @NotNull(message = "The price value must be positive number")
    private BigDecimal price;
    private Boolean active = true;
}
