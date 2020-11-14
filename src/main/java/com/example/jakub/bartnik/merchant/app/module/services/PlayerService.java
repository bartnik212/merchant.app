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
    private int coins; // = 25
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

        Good goodType = null;

        if (citySelected == City.GDANSK &&
                cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT &&
                listOfGoods.contains(Good.WOOD)) {

            goodType = Good.WOOD;

        } else if (citySelected == City.WARSAW &&
                cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT &&
                listOfGoods.contains(Good.IRON)) {

            goodType = Good.IRON;

        } else if (citySelected == City.ZAKOPANE &&
                cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT &&
                listOfGoods.contains(Good.COPPER)) {

            goodType = Good.COPPER;
        }

        return goodType;
    }

    public VacationPlace getCurrentlyVisitingVacationPlace() {

        VacationPlace vacationPlace = null;

        if (citySelected == City.GDANSK &&
                cityActionSelected == CityAction.GO_ON_VACATION) {

            vacationPlace = VacationPlace.MOTLAWA;

        } else if (citySelected == City.WARSAW &&
                cityActionSelected == CityAction.GO_ON_VACATION) {
            vacationPlace = VacationPlace.VISTULA;

        } else if (citySelected == City.ZAKOPANE &&
                cityActionSelected == CityAction.GO_ON_VACATION) {
            vacationPlace = VacationPlace.GUBALOWKA;
        }

        return vacationPlace;
    }

    public City getCurrentlyVisitingLocalCompany() {

        City localCompany = null;

        if (citySelected == City.GDANSK &&
                cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY) {

            localCompany = City.GDANSK;

        } else if (citySelected == City.WARSAW &&
                cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY) {
            localCompany = City.WARSAW;

        } else if (citySelected == City.ZAKOPANE &&
                cityActionSelected == CityAction.GO_TO_LOCAL_COMPANY) {
            localCompany = City.ZAKOPANE;
        }

        return localCompany;
    }

    public PositiveAnswerAction getPositiveAnswer () {

        PositiveAnswerAction positiveAnswerAction = null;

        if (citySelected == City.GDANSK && cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT){

            positiveAnswerAction = PositiveAnswerAction.GOOD_MERCHANT_GDANSK;

        } else if (citySelected == City.WARSAW && cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT) {

            positiveAnswerAction = PositiveAnswerAction.GOOD_MERCHANT_WARSAW;

        } else if (citySelected == City.ZAKOPANE && cityActionSelected == CityAction.MEET_WITH_GOOD_MERCHANT) {

            positiveAnswerAction = PositiveAnswerAction.GOOD_MERCHANT_ZAKOPANE;
        }

        return positiveAnswerAction;
    }





}

