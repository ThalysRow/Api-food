package com.api_food.Algaworks_Food.dto.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListProductInOrderDTO {
    @NotNull(message = "The field id is required")
    @Positive(message = "The field id must be a positive number")
    private int id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    @JsonIgnore
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal price;
    @JsonIgnore
    private Boolean active;
}
