package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.domain.filter.DailySalesFilter;

public interface SaleReportService {

    byte[] generateDailySalesReport(DailySalesFilter filter, String timeZone);
}
