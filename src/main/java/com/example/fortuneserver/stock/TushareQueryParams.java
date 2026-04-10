package com.example.fortuneserver.stock;

import java.util.Map;

public record TushareQueryParams(
    String apiName,
    String exchange,
    String area,
    Map<String, String> filters,
    String rawQuery
) {
}
