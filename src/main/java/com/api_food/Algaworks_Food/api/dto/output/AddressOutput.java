package com.api_food.Algaworks_Food.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressOutput {
    private String zipcode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private CityOutput city;
}
