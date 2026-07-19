package com.api_food.Algaworks_Food.domain.filter;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DailySalesFilter {

    private UUID restaurantId;
    private LocalDate createdAtFrom;
    private LocalDate createdAtUntil;
}
