package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.output.DailySalesOutput;
import com.api_food.Algaworks_Food.domain.filter.DailySalesFilter;
import com.api_food.Algaworks_Food.infra.SalesQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final SalesQueryService salesQueryService;

    @GetMapping("/daily-sales")
    @ResponseStatus(HttpStatus.OK)
    public List<DailySalesOutput> searchDailySales(DailySalesFilter filter,
                                                   @RequestParam (required = false, defaultValue = "+00:00")
                                                   String timeZone){
        return salesQueryService.searchDailySales(filter, timeZone);
    }
}
