package com.fortune.majorservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = {"com.fortune.majorservice.infrastructure.mapper"})
public class MajorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajorServiceApplication.class, args);
    }

}
