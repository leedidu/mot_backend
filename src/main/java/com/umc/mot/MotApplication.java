package com.umc.mot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MotApplication.class, args);
    }

}
