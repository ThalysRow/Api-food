package com.api_food.Algaworks_Food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("hibernateLazyInitializer")
    @JoinColumn(nullable = false)
    private KitchenModel kitchen;

    @Embedded
    @JsonIgnore
    private AddressModel address;

    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime dateCreated;

    @Column(nullable = false, columnDefinition = "datetime")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime dateUpdated;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "restaurant_paymentMethods", joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private List<PaymentMethodModel> paymentMethods;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<ProductModel> products;
}
