package com.api_food.Algaworks_Food.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
