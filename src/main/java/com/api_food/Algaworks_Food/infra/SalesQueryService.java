package com.api_food.Algaworks_Food.infra;

import com.api_food.Algaworks_Food.api.dto.output.DailySalesOutput;
import com.api_food.Algaworks_Food.domain.filter.DailySalesFilter;

import java.util.List;

public interface SalesQueryService {

    List<DailySalesOutput> searchDailySales(DailySalesFilter filter);
}
