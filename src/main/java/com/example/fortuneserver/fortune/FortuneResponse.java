package com.example.fortuneserver.fortune;

import java.util.Map;

public record FortuneResponse(
    String input,
    String apiName,
    Map<String, String> params,
    Map<String, String> filters
) {
}
