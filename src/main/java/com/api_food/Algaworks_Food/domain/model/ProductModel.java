package com.api_food.Algaworks_Food.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(nullable = false)
    private RestaurantModel restaurant;

    @OneToMany(mappedBy = "product")
    private List<OrderItemModel> orderItems;

    public static ProductModel addProduct(String name, String description,
                                          BigDecimal price, RestaurantModel restaurant){
        ProductModel product  = new ProductModel();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setRestaurant(restaurant);
        product.setActive(true);

        return product;
    }
}
