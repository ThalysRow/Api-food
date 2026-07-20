package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.output.DailySalesOutput;
import com.api_food.Algaworks_Food.domain.filter.DailySalesFilter;
import com.api_food.Algaworks_Food.domain.service.SaleReportService;
import com.api_food.Algaworks_Food.infra.service.query.SalesQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final SalesQueryService salesQueryService;
    private final SaleReportService saleReportService;

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<DailySalesOutput> searchDailySales(DailySalesFilter filter,
                                                   @RequestParam (required = false, defaultValue = "+00:00")
                                                   String timeZone){
        return salesQueryService.searchDailySales(filter, timeZone);
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> searchDailySalesPdf(DailySalesFilter filter,
                                              @RequestParam (required = false, defaultValue = "+00:00")
                                                   String timeZone){

        byte[] bytesPdf = saleReportService.generateDailySalesReport(filter, timeZone);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

}
