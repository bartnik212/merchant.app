package com.example.jakub.bartnik.merchant.app.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
public class ApplicationProperties {

    @Value("${message1}")
    private String message1;

    @Value("${message2}")
    private String message2;

    @Value("${message3}")
    private String message3;

    @Value("${message4}")
    private String message4;

    @Value("${message5}")
    private String message5;

    @Value("${woodMerchantDialog}")
    private String woodMerchantDialog;

    @Value("${ironMerchantDialog")
    private String ironMerchantDialog;

    @Value("${copperMerchantDialog")
    private String copperMerchantDialog;
}
