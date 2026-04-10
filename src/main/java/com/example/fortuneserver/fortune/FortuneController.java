package com.example.fortuneserver.fortune;

import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FortuneController {

    private final FortuneService fortuneService;

    public FortuneController(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
            "application", "fortune-server",
            "runtime", "spring-boot",
            "javaVersion", "17"
        );
    }

    @PostMapping("/fortune")
    public FortuneResponse generate(@Valid @RequestBody FortuneRequest request) {
        return fortuneService.generate(request);
    }
}
