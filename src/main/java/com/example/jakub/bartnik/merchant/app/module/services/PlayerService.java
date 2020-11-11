package com.example.jakub.bartnik.merchant.app.module.services;

import com.example.jakub.bartnik.merchant.app.module.enums.goods.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedList;
import java.util.List;

@Data
@Component
@SessionScope
@Slf4j
public class PlayerService {

    private String name;
    private List<Good> listOfGoods = new LinkedList<>();
    private List<Weapon> listOfWeapons = new LinkedList<>();
    private Weapon weaponSelected;
    private int coins;
    private int healthPoints; // = 100
    private City citySelected;
    private GameState gameState = GameState.ENTER_NAME;
    private Answer answer;

    private CityAction cityActionSelected;


    //lista city, miasta, w ktorych odiwedzielem warsztat
    //metody miast, a konkretnie te, ktore sie roznia

    public void saveGood(Good good) {
        listOfGoods.add(good);
        log.info("chosen good: " + listOfGoods);

    }


    public void saveWeapon(Weapon weapon) {
        listOfWeapons.add(weapon);
        log.info("chosen weapon: " + listOfWeapons);
    }

    public Good getCurrentlyVisitingMerchantGood() {
        PlayerService playerService = new PlayerService();

        Good goodType = null;

        if (playerService.getCitySelected() == City.GDANSK &&
                playerService.getCityActionSelected() == CityAction.MEET_WITH_GOOD_MERCHANT) {

            goodType = Good.WOOD;

        } else if (playerService.getCitySelected() == City.WARSAW &&
                playerService.getCityActionSelected() == CityAction.MEET_WITH_GOOD_MERCHANT) {

            goodType = Good.IRON;

        } else if (playerService.getCitySelected() == City.ZAKOPANE &&
                playerService.getCityActionSelected() == CityAction.MEET_WITH_GOOD_MERCHANT) {

            goodType = Good.COPPER;
        }

        return goodType;
    }

    public String getCurrentlyVisitingVacationPlace() {
        PlayerService playerService = new PlayerService();

        String vacationPlace = "";

        if (playerService.getCitySelected() == City.GDANSK &&
                playerService.getCityActionSelected() == CityAction.GO_ON_VACATION) {

            vacationPlace = "motlawa";

        } else if (playerService.getCitySelected() == City.WARSAW &&
                playerService.getCityActionSelected() == CityAction.GO_ON_VACATION) {
            vacationPlace = "vistula";

        } else if (playerService.getCitySelected() == City.ZAKOPANE &&
                playerService.getCityActionSelected() == CityAction.GO_ON_VACATION) {
            vacationPlace = "gubalowka";
        }

        return vacationPlace;
    }
}
