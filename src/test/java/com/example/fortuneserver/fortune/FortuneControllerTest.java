package com.example.fortuneserver.fortune;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void shouldReturnPersonalizedFortune() throws Exception {
        mockMvc.perform(post("/api/v1/fortune")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "name": "Cursor",
                      "topic": "CI/CD"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.recipient").value("Cursor"))
            .andExpect(jsonPath("$.topic").value("CI/CD"))
            .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void shouldRejectBlankName() throws Exception {
        mockMvc.perform(post("/api/v1/fortune")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "name": "",
                      "topic": "CI/CD"
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Validation failed"));
    }
}
