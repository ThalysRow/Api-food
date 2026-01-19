package com.api_food.Algaworks_Food.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "kitchens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "kitchen")
    private List<RestaurantModel> restaurants;

    public static KitchenModel addKitchen(String name) {
        KitchenModel kitchen = new KitchenModel();
        kitchen.setName(name);
        return kitchen;
    }
}
