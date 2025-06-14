package com.api_food.Algaworks_Food.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressListDTO {
    private String zipcode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private CityListDTO city;
}
