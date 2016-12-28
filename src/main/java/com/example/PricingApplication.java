package com.example;

import static java.util.Arrays.stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PricingApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(PricingApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx)
    {
        return args -> {
            System.out.println("Beans provided by Spring Boot:");

            String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
            stream(beanDefinitionNames).sorted().forEach(System.out::println);
        };
    }
}
