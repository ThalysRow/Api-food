package com.api_food.Algaworks_Food.dto.list;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantListDTO {
    @NotNull(message = "The field id is required")
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    private BigDecimal deliveryFee;
    private KitchenListDTO kitchen;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private Boolean active;
    private Boolean open;
    private AddressListDTO address;
    private UserListDTO user;
}
