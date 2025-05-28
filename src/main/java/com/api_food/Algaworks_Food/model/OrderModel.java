package com.api_food.Algaworks_Food.model;

import com.api_food.Algaworks_Food.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private BigInteger subtotal;

    @Column(nullable = false)
    private BigInteger deliveryFee;

    @Column(nullable = false)
    private BigInteger totalValue;

    private OrderStatus status;

    @Embedded
    private AddressModel deliveryAddress;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PaymentMethodModel paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserModel user;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dateCreated;

    private LocalDateTime dateConfirmed;

    private LocalDateTime dateCanceled;

    private LocalDateTime dateDelivered;

    @OneToMany(mappedBy = "order")
    private List<OrderItemModel> itens;
}
