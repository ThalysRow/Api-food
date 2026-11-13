package com.api_food.Algaworks_Food.domain.model;

import com.api_food.Algaworks_Food.domain.enums.OrderStatus;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OrderModel extends AbstractAggregateRoot<OrderModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false, name = "subtotal")
    private BigDecimal subtotal;

    @Column(nullable = false, name = "delivery_fee")
    private BigDecimal deliveryFee;

    @Column(nullable = false, name = "total_value")
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Embedded
    private AddressModel deliveryAddress;

    @ManyToOne(fetch =  FetchType.LAZY)
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
    private OffsetDateTime dateCancelled;

    @Column(name = "date_delivered", columnDefinition = "datetime")
    private OffsetDateTime dateDelivered;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItemModel> itens;

    public void calcTotalValue(){
        getItens().forEach(OrderItemModel::calcPriceTotal);

        this.subtotal = getItens().stream()
                .map(OrderItemModel::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalValue = this.subtotal.add(this.deliveryFee != null ? this.deliveryFee : BigDecimal.ZERO);

    }

    public void confirm(){

        if(!this.getStatus().equals(OrderStatus.CREATED)){
            throw new BusinessException(String.format("The order with id '%d' cannot be confirmed, " +
                    "because the status of the order is '%s'", this.getId(), this.getStatus()));
        }

        setStatus(OrderStatus.CONFIRMED);
        setDateConfirmed(OffsetDateTime.now());
    }

    public void deliver(){

        if(!this.getStatus().equals(OrderStatus.CONFIRMED)){
            throw new BusinessException(String.format("The order with id '%d' cannot be delivered, " +
                    "because the status of the order is '%s'", this.getId(), this.getStatus()));
        }

        setStatus(OrderStatus.DELIVERED);
        setDateDelivered(OffsetDateTime.now());
    }

    public void cancel(){

        if(!this.getStatus().equals(OrderStatus.CREATED) &&
                !this.getStatus().equals(OrderStatus.CONFIRMED)){
            throw new BusinessException(String.format("The order with id '%d' cannot be canceled, " +
                    "because the status of the order is '%s'", this.getId(), this.getStatus()));
        }

        setStatus(OrderStatus.CANCELED);
        setDateCancelled(OffsetDateTime.now());
    }

    public void setItens(List<OrderItemModel> itens){
        this.itens = itens;
        if (this.itens != null){
            this.itens.forEach(item -> item.setOrder(this));
        }
    }
}
