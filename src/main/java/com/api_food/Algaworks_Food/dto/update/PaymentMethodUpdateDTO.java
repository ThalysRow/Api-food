package com.api_food.Algaworks_Food.dto.update;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodUpdateDTO {
    private int id;
    @NotBlank(message = "Payment method name is required")
    private String name;
}
