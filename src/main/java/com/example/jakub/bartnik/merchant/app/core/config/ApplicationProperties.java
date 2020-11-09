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


    @Value("${meetWithGoodMerchant}")
    private String goodMerchantDialog;

    @Value("${goOnVacation}")
    private String goOnVacationDialog;

    @Value("${changeTheCity}")
    private String changeTheCityDialog;

    @Value("${randomAction}")
    private String randomActionDialog;

    @Value("${goToLocalCompany}")
    private String goToLocalCompanyDialog;

    @Value(("${goToWeaponStore}"))
    private String goToWeaponStoreDialog;

    @Value("${chooseWeaponToFight}")
    private String chooseWeaponToFightDialog;

//    @Value("${listOfCityActionsDialogs")
//    private List<String> cityActionsDialogs = List.of(goodMerchantDialog,
//            goOnVacationDialog,
//            changeTheCityDialog,
//            randomActionDialog,
//            goToLocalCompanyDialog,
//            goToWeaponStoreDialog,
//            chooseWeaponToFightDialog);
}
