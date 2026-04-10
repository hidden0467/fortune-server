package com.example.fortuneserver.fortune;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
class FortuneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnProjectMetadata() throws Exception {
        mockMvc.perform(get("/api/v1/info"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.application").value("fortune-server"))
            .andExpect(jsonPath("$.javaVersion").value("17"));
    }

    @Test
    void shouldBuildTushareQueryWithCombinedConditions() throws Exception {
        mockMvc.perform(post("/api/v1/fortune")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "input": "帮我筛选上证的广东股票"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.input").value("帮我筛选上证的广东股票"))
            .andExpect(jsonPath("$.apiName").value("stock_basic"))
            .andExpect(jsonPath("$.params.exchange").value("SSE"))
            .andExpect(jsonPath("$.params.area").value("广东"))
            .andExpect(jsonPath("$.filters.roe").value("> 0"))
            .andExpect(jsonPath("$.filters.netProfit").value("> 0"));
    }

    @Test
    void shouldMapShanghaiToExchangeAndArea() throws Exception {
        mockMvc.perform(post("/api/v1/fortune")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "input": "请找一下上海的股票"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.params.exchange").value("SSE"))
            .andExpect(jsonPath("$.params.area").value("上海"));
    }

    @Test
    void shouldMapShenzhenToExchangeAndArea() throws Exception {
        mockMvc.perform(post("/api/v1/fortune")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "input": "想看深圳本地的股票"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.params.exchange").value("SZSE"))
            .andExpect(jsonPath("$.params.area").value("深圳"));
    }

    @Test
    void shouldRejectBlankInput() throws Exception {
        mockMvc.perform(post("/api/v1/fortune")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "input": ""
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Validation failed"));
    }
}
