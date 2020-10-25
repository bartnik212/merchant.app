package com.example.jakub.bartnik.merchant.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

    @Value("${message1}")
    private String message1;

    public String getMessage1() {
        return message1;
    }
}
