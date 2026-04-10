package com.example.fortuneserver.stock;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StockQueryController {

    private final StockQueryService stockQueryService;

    public StockQueryController(StockQueryService stockQueryService) {
        this.stockQueryService = stockQueryService;
    }

    @PostMapping("/stock/query")
    public TushareQueryParams queryStock(@Valid @RequestBody StockQueryRequest request) {
        return stockQueryService.parse(request);
    }
}
