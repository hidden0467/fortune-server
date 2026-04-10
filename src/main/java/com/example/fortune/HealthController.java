package com.example.fortune;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final Instant startedAt = Instant.now();

    @GetMapping("/")
    public Map<String, Object> index() {
        return Map.of(
                "service", "fortune-server",
                "status", "UP",
                "startedAt", startedAt.toString()
        );
    }
}
