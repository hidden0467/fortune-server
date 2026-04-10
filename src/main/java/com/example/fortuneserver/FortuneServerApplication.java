package com.example.fortuneserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FortuneServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FortuneServerApplication.class, args);
    }
}
