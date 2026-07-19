package com.api_food.Algaworks_Food.domain.filter;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class DailySalesFilter {

    private UUID restaurantId;
    private OffsetDateTime createdAtFrom;
    private OffsetDateTime createdAtUntil;
}
