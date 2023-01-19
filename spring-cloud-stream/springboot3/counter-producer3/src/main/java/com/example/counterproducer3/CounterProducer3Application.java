package com.example.counterproducer3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

@Slf4j
@SpringBootApplication
public class CounterProducer3Application {

    public static void main(String[] args) {
        SpringApplication.run(CounterProducer3Application.class, args);
    }

    private long counter;

    @Bean
    public Supplier<Long> counter() {
        return () -> {
            log.info("Send counter [{}]",counter);
            return counter++;
        };
    }
}
