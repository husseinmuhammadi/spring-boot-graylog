package com.javastudio.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.StringJoiner;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        LOGGER.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        LOGGER.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            LOGGER.info("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            Arrays.stream(beanNames).forEach(LOGGER::info);
        };
    }
}
