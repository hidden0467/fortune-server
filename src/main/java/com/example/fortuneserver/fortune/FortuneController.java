package com.example.fortuneserver.fortune;

import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FortuneController {

    private final FortuneService fortuneService;
    private final TushareProperties tushareProperties;

    public FortuneController(FortuneService fortuneService, TushareProperties tushareProperties) {
        this.fortuneService = fortuneService;
        this.tushareProperties = tushareProperties;
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
            "application", "fortune-server",
            "runtime", "spring-boot",
            "javaVersion", "17"
        );
    }

    @GetMapping("/tushare/config")
    public TushareConfigResponse tushareConfig() {
        return new TushareConfigResponse(tushareProperties.isTokenConfigured());
    }

    @PostMapping(
        path = {"/fortune", "/tushare/query"},
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public FortuneResponse generate(@Valid @RequestBody FortuneRequest request) {
        return fortuneService.generate(request);
    }
}
