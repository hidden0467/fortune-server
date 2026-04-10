package com.example.fortuneserver.fortune;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class FortuneService {

    private static final List<String> FORTUNES = List.of(
        "Ship small changes continuously.",
        "Automate everything that is repeatable.",
        "A green pipeline is a deployable pipeline.",
        "Observability makes operations calmer.",
        "Tests turn fear into feedback."
    );

    public FortuneResponse generate(FortuneRequest request) {
        String recipient = request.name().trim();
        String topic = request.topic() == null || request.topic().isBlank()
            ? "general"
            : request.topic().trim();
        String message = FORTUNES.get(ThreadLocalRandom.current().nextInt(FORTUNES.size()));

        return new FortuneResponse(
            recipient,
            topic,
            message,
            OffsetDateTime.now()
        );
    }

    public FortuneResponse randomFortune(String topic) {
        return generate(new FortuneRequest("guest", topic));
    }
}
