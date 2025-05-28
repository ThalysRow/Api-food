package com.api_food.Algaworks_Food.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {
    @Column(name = "address_zipcode")
    private String zipcode;
    @Column(name = "address_street")
    private String street;
    @Column(name = "address_number")
    private String number;
    @Column(name = "address_complement")
    private String complement;
    @Column(name = "address_neighborhood")
    private String neighborhood;
    @ManyToOne
    @JoinColumn(name = "address_city_id")
    private CityModel city;
}
