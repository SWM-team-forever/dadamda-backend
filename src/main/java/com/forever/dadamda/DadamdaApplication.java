package com.forever.dadamda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DadamdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DadamdaApplication.class, args);
    }

}
