package com.example.pium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PiUmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiUmApplication.class, args);
    }

}
