package com.api_food.Algaworks_Food.api.dto.output;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantOutput {
    @NotNull(message = "The field id is required")
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    private BigDecimal deliveryFee;
    private KitchenOutput kitchen;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private Boolean active;
    private Boolean open;
    private AddressOutput address;
    private UserOutput user;
}
