package com.example.fortuneserver.fortune;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tushare")
public record TushareProperties(String token) {

    public boolean isTokenConfigured() {
        return token != null && !token.isBlank();
    }
}
