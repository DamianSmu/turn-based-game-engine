package com.example.engine;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class SpringApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }


    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            //mapRepository.findAll().get(0);
            //System.out.println(mapRepository.findAll());
        };
    }

}
