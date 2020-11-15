package com.example.jakub.bartnik.merchant.app.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


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

    @Value("${ironMerchantDialog}")
    private String ironMerchantDialog;

    @Value("${copperMerchantDialog}")
    private String copperMerchantDialog;

    @Value("${motlawaDialog}")
    private String motlawaDialog;

    @Value("${vistulaDialog}")
    private String vistulaDialog;

    @Value("${gubalowkaDialog}")
    private String gubalowkaDialog;

    @Value("${copperSmelterDialog}")
    private String copperSmelterDialog;

    @Value("${workerCopperSmelterDialog}")
    private String workerCopperSmelterDialog;

    @Value("${workerCopperSmelterDialog2}")
    private String workerCopperSmelterDialog2;

    @Value("${ironWorksDialog}")
    private String ironWorksDialog;

    @Value("${noGoodDialog}")
    private String noGoodDialog;

    @Value("${sawmillDialog}")
    private String sawmillDialog;

    @Value("${weaponStoreDialog}")
    private String weaponStoreDialog;

    @Value("${woodMerchantPositiveAnswer}")
    private String woodMerchantPositiveAnswer;

    @Value("${ironMerchantPositiveAnswer}")
    private String ironMerchantPositiveAnswer;

    @Value("${copperMerchantPositiveAnswer}")
    private String copperMerchantPositiveAnswer;

    @Value("${merchantNegativeAnswer}")
    private String merchantNegativeAnswer;

    @Value("${duplicatedWeapon}")
    private String duplicatedWeapon;

    @Value("${weaponBought}")
    private String weaponBought;

    @Value("${winDialog}")
    private String winDialog;




}
