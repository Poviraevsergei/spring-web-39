package com.tms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "Spring web 39 learn app",
        description = "Test app for learn Spring framework",
        contact = @Contact(
                name = "Sergey Poviraev",
                email = "java@gmail.com",
                url = "https://teachmeskills.by/"
        )
))
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

//TODO: props


