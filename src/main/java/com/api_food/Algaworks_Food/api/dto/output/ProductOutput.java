package com.api_food.Algaworks_Food.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOutput {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
