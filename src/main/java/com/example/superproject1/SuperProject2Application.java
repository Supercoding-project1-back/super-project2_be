package com.example.superproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SuperProject2Application {

    public static void main(String[] args) {
        SpringApplication.run(SuperProject2Application.class, args);
    }

}
