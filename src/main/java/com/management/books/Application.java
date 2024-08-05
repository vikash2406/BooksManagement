package com.management.books;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PreDestroy;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Book Management System", version = "2.0", description = "Books Info"))
@EnableCaching
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @PreDestroy
    public void onShutdown(){
        LOGGER.info("Application context is closing.....");
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

    }
}
