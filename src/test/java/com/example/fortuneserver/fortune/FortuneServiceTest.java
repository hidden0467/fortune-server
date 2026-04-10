package com.example.fortuneserver.fortune;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class FortuneServiceTest {

    private final FortuneService fortuneService = new FortuneService();

    @Test
    void shouldReturnAreaWithoutExchangeWhenOnlyAreaIsMentioned() {
        FortuneResponse response = fortuneService.generate(new FortuneRequest("帮我看一下北京的股票"));

        assertEquals("stock_basic", response.apiName());
        assertEquals("北京", response.params().get("area"));
        assertFalse(response.params().containsKey("exchange"));
        assertEquals("> 0", response.filters().get("roe"));
        assertEquals("> 0", response.filters().get("netProfit"));
    }

    @Test
    void shouldPreferTheFirstExchangeMentionedInInput() {
        FortuneResponse response = fortuneService.generate(new FortuneRequest("先看深成再看上证"));

        assertEquals("SZSE", response.params().get("exchange"));
    }

    @Test
    void shouldNormalizeProvinceAliases() {
        FortuneResponse response = fortuneService.generate(new FortuneRequest("帮我找广东省的股票"));

        assertEquals("广东", response.params().get("area"));
    }
}
