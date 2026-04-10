package com.example.fortuneserver.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StockQueryRequest(
    @NotBlank(message = "query must not be blank")
    @Size(max = 200, message = "query length must be <= 200")
    String query
) {
}
