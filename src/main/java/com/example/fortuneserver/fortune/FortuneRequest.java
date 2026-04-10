package com.example.fortuneserver.fortune;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FortuneRequest(
    @NotBlank(message = "input must not be blank")
    @Size(max = 200, message = "input length must be <= 200")
    String input
) {
}
