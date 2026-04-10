package com.example.fortuneserver.fortune;

import java.time.OffsetDateTime;

public record FortuneResponse(
    String recipient,
    String topic,
    String message,
    OffsetDateTime generatedAt
) {
}
