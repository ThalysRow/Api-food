package com.api_food.Algaworks_Food.model;

import com.api_food.Algaworks_Food.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.OffsetDateTime;
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
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false, name = "subtotal")
    private BigInteger subtotal;

    @Column(nullable = false, name = "delivery_fee")
    private BigInteger deliveryFee;

    @Column(nullable = false, name = "total_value")
    private BigInteger totalValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Embedded
    private AddressModel deliveryAddress;

    @ManyToOne
    @JoinColumn(nullable = false, name = "payment_method_id")
    private PaymentMethodModel paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false, name = "restaurant_id")
    private RestaurantModel restaurant;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserModel user;

    @Column(nullable = false, name = "date_created", columnDefinition = "datetime")
    @CreationTimestamp
    private OffsetDateTime dateCreated;

    @Column(name = "date_confirmed", columnDefinition = "datetime")
    private OffsetDateTime dateConfirmed;

    @Column(name = "date_cancelled", columnDefinition = "datetime")
    private OffsetDateTime dateCanceled;

    @Column(name = "date_delivered", columnDefinition = "datetime")
    private OffsetDateTime dateDelivered;

    @OneToMany(mappedBy = "order")
    private List<OrderItemModel> itens;
}
