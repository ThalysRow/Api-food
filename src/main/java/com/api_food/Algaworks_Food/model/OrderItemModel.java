package com.api_food.Algaworks_Food.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "quantity")
    private BigDecimal quantity;

    @Column(nullable = false, name = "unit_price")
    private BigDecimal unitPrice;

    @Column(nullable = false, name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "observations")
    private String observations;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "order_id")
    private OrderModel order;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "product_id")
    private ProductModel product;

}
