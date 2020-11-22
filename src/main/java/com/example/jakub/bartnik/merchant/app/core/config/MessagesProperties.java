package com.example.jakub.bartnik.merchant.app.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class MessagesProperties {

    @Value("${helloMessage}")
    private String helloMessage;

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

    @Value("${workerIronWorksDialog}")
    private String workerIronWorksDialog;

    @Value("${workerIronWorksDialog2}")
    private String workerIronWorksDialog2;

    @Value("${noGoodDialog}")
    private String noGoodDialog;

    @Value("${sawmillDialog}")
    private String sawmillDialog;

    @Value("${workerSawmillDialog}")
    private String workerSawmillDialog;

    @Value("${workerSawmillDialog2}")
    private String workerSawmillDialog2;

    @Value("${winDialogLocalCompanyCopper}")
    private String winDialogLocalCompanyCopper;

    @Value("${winDialogLocalCompanyIron}")
    private String winDialogLocalCompanyIron;

    @Value("${winDialogLocalCompanyWood}")
    private String winDialogLocalCompanyWood;

    @Value("${loseDialogLocalCompany}")
    private String loseDialogLocalCompany;

    @Value("${drawDialogLocalCompany}")
    private String drawDialogLocalCompany;

    @Value("${leaveLocalCompany}")
    private String leaveLocalCompany;

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

    @Value("${findCoins}")
    private String findCoins;

    @Value("${findWood}")
    private String findWood;

    @Value("${findCopper}")
    private String findCopper;

    @Value("${findIron}")
    private String findIron;

    @Value("${robberDialog}")
    private String robberDialog;

    @Value("${robberSwordShield}")
    private String robberSwordShield;

    @Value("${robberTwoHandedSword}")
    private String robberTwoHandedSword;

    @Value("${robberFireArm}")
    private String robberFireArm;

    @Value("${winRobberDialog}")
    private String winRobberDialog;

    @Value("${loseRobberDialog}")
    private String loseRobberDialog;

    @Value("${drawRobberDialog}")
    private String drawRobberDialog;

    @Value("${runAwayFromRobber}")
    private String runAwayFromRobber;

    @Value("${healthPointsBelow0}")
    private String healthPointsBelow0;

    @Value("${youWonTheGame}")
    private String youWonTheGame;

    @Value("${notEnoughCoins}")
    private String notEnoughCoins;

    @Value("${youHaveAlreadyBeenHere}")
    private String youHaveAlreadyBeenHere;


}
