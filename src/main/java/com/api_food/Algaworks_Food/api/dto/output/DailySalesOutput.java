package com.api_food.Algaworks_Food.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class DailySalesOutput {
    private LocalDate date;
    private Long totalSales;
    private BigDecimal totalValue;
}
