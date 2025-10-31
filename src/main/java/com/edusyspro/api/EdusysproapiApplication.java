package com.edusyspro.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EdusysproapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdusysproapiApplication.class, args);
    }

}
