package com.example.fortune;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FortuneController {

    private static final List<String> FORTUNES = List.of(
            "Today is your lucky day!",
            "A beautiful, smart, and loving person will come into your life.",
            "A dubious friend may be an enemy in camouflage.",
            "A faithful friend is a strong defense.",
            "A fresh start will put you on your way.",
            "Good things come to those who deploy often."
    );

    @GetMapping("/fortune")
    public Fortune randomFortune() {
        int idx = ThreadLocalRandom.current().nextInt(FORTUNES.size());
        return new Fortune(idx, FORTUNES.get(idx));
    }

    public record Fortune(int id, String message) {}
}
