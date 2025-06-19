package com.api_food.Algaworks_Food.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_methods")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentMethodModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "paymentMethod")
    private List<OrderModel> orders;

    @ManyToMany(mappedBy = "paymentMethods")
    private List<RestaurantModel> restaurants;
}
