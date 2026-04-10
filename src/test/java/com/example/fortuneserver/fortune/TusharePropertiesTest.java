package com.example.fortuneserver.fortune;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TusharePropertiesTest {

    @Test
    void shouldReturnFalseWhenTokenIsBlank() {
        TushareProperties properties = new TushareProperties("   ");

        assertFalse(properties.isTokenConfigured());
    }

    @Test
    void shouldReturnTrueWhenTokenIsPresent() {
        TushareProperties properties = new TushareProperties("demo-token");

        assertTrue(properties.isTokenConfigured());
    }
}
