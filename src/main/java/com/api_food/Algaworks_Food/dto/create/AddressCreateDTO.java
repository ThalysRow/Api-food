package com.api_food.Algaworks_Food.dto.create;

import com.api_food.Algaworks_Food.dto.list.CityListDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateDTO {
    @NotBlank(message = "Zipcode is required")
    private String zipcode;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "Number is required")
    private String number;
    private String complement;
    @NotBlank(message = "Neighbordhood is required")
    private String neighborhood;
    @Valid
    @NotNull(message = "City is required")
    private CityListDTO city;
}
