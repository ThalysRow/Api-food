package com.api_food.Algaworks_Food.api.dto.input;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class OrderFilterInput {
    private UUID userId;
    private UUID restaurantId;
    private OffsetDateTime createdAtFrom;
    private OffsetDateTime createdAtUntil;
}
