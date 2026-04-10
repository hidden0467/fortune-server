package com.example.fortuneserver.stock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StockQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTushareParamsForShanghaiExchange() throws Exception {
        mockMvc.perform(post("/api/v1/stock/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "query": "查询上证广东的股票" }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.apiName").value("stock_basic"))
            .andExpect(jsonPath("$.exchange").value("SSE"))
            .andExpect(jsonPath("$.area").value("广东"))
            .andExpect(jsonPath("$.filters.exchange").value("SSE"))
            .andExpect(jsonPath("$.filters.area").value("广东"))
            .andExpect(jsonPath("$.filters.roe_min").value("0"))
            .andExpect(jsonPath("$.filters.profit_min").value("0"));
    }

    @Test
    void shouldReturnTushareParamsForShenzhenExchange() throws Exception {
        mockMvc.perform(post("/api/v1/stock/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "query": "深圳北京股票" }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.exchange").value("SZSE"))
            .andExpect(jsonPath("$.area").value("北京"));
    }

    @Test
    void shouldRejectBlankQuery() throws Exception {
        mockMvc.perform(post("/api/v1/stock/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "query": "" }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void shouldHandleQueryWithNoExchangeOrArea() throws Exception {
        mockMvc.perform(post("/api/v1/stock/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "query": "所有股票" }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.apiName").value("stock_basic"))
            .andExpect(jsonPath("$.exchange").isEmpty())
            .andExpect(jsonPath("$.area").isEmpty())
            .andExpect(jsonPath("$.filters.roe_min").value("0"))
            .andExpect(jsonPath("$.filters.profit_min").value("0"));
    }
}
