package com.api_food.Algaworks_Food.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class CityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private StateModel state;

   public static CityModel createCity(String name, StateModel state){
       CityModel city = new CityModel();
       city.setName(name);
       city.setState(state);

       return city;
   }

}
