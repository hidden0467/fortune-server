package com.example.fortuneserver.fortune;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FortuneRequest(
    @NotBlank(message = "name must not be blank")
    @Size(max = 50, message = "name length must be <= 50")
    String name,
    @Size(max = 30, message = "topic length must be <= 30")
    String topic
) {
}
