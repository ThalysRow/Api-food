package com.api_food.Algaworks_Food.dto.create;

import com.api_food.Algaworks_Food.dto.list.ListProductInOrderDTO;
import com.api_food.Algaworks_Food.dto.list.ProductListDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateDTO {
    @Valid
    @JsonIgnoreProperties(value = {"description", "active"}, allowGetters = true)
    private ListProductInOrderDTO product;
    @NotNull(message = "The quantity field is required")
    @Positive(message = "The quantity field must be a positive number")
    private BigDecimal quantity;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal unitPrice;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalPrice;
    private String observations;
}
