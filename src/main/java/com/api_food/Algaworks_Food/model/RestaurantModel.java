package com.api_food.Algaworks_Food.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")
public class RestaurantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(name = "delivery_fee", nullable = false)
    private BigDecimal deliveryFee;
    @ManyToOne
    @JoinColumn(nullable = false)
    private KitchenModel kitchen;
}
