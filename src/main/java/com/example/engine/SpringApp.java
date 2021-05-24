package com.example.engine;

import com.example.engine.api.repository.MapRepository;
import com.example.engine.model.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.HashSet;

@SpringBootApplication
@EnableMongoRepositories
public class SpringApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }


    @Bean
    CommandLineRunner initDatabase(MapRepository mapRepository) {
        return args -> {
            if(mapRepository.count() == 0){
                mapRepository.save(new Map(50, 1));
            }
            //System.out.println(mapRepository.findAll());
        };
    }
}
